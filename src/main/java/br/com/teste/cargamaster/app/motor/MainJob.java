package br.com.teste.cargamaster.app.motor;

import br.com.teste.cargamaster.app.motor.domain.Cliente1.Pessoa;
import br.com.teste.cargamaster.app.motor.domain.Fabricabusiness;
import br.com.teste.cargamaster.app.motor.jobutils.JobExecutionUtils;
import br.com.teste.cargamaster.app.motor.jobutils.JobStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class MainJob {

    private final ConcurrentHashMap<String, Future<?>> activeJobs = new ConcurrentHashMap<>();
    private final JobLauncher jobLauncher;
    private final JobStatus jobStatus;
    private final Job job;
    private final JdbcClient jdbcClient;

    @Autowired
    @Qualifier("transactionManagerApp")
    private PlatformTransactionManager transactionManager;

    public MainJob(JobLauncher jobLauncher, JobStatus jobStatus, @Lazy Job job, JdbcClient jdbcClient) {
        this.jobLauncher = jobLauncher;
        this.jobStatus = jobStatus;
        this.job = job;
        this.jdbcClient = jdbcClient;
    }

    //-------------------------------------MOTOR INICIO--------------------------------------------

    @Async
    public void executeJobMain(JobParameters preJobResultado) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        String idCarga = preJobResultado.getString("idCarga");
        String emsNumero = preJobResultado.getString("emsNumero");

        if (idCarga != null && emsNumero != null &&
                activeJobs.containsKey(idCarga) && activeJobs.containsKey(emsNumero)) {
            throw new IllegalStateException("Um job com este parâmetro já está em execução.");
        }

        JobExecution jobExecution = jobLauncher.run(job, preJobResultado);
        jobStatus.EnvioStatusParaPosJob(jobExecution.getStatus());

        //Para apresentar o teste no terminal -- remover quando nao testar mais
        jdbcClient.
                sql(" SELECT * FROM TB_PESSOA; ")
                .query(Pessoa.class)
                .stream().forEach(p -> log.info("Encontrado <{{}}> no database.", p));
    }

    @Bean
    public Job job(Step step, JobRepository jobRepository) {
        return new JobBuilder("job", jobRepository)
                .listener(tempoExecucaoJob())
                .start(step)
                .next(moverArquivoStep(jobRepository))
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step step(ItemReader<Map<String, Object>> reader, ItemWriter<Map<String, Object>> writer, JobRepository jobRepository) {
        return new StepBuilder("step", jobRepository)
                .<Map<String, Object>, Map<String, Object>>chunk(200, transactionManager)
                .reader(reader)
                //.processor(...) // Adicione seu processador se necessário
                .writer(writer)
                .build();
    }

    @Bean
    @JobScope
    public FlatFileItemReader<Map<String, Object>> reader(@Value("#{jobExecution}") JobExecution jobExecution) {
        JobParameters jobParameters = jobExecution.getJobParameters();
        Fabricabusiness fabricabusiness = new Fabricabusiness();
        final List<String> resultadoCampos = fabricabusiness.gerarCamposParaProcessamento(jobParameters);

        FlatFileItemReader<Map<String, Object>> reader = new FlatFileItemReader<>();
        reader.setName("reader");
        reader.setResource(new FileSystemResource("carga/pessoas.csv")); // Certifique-se de que o caminho do arquivo está correto

        DefaultLineMapper<Map<String, Object>> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer() {{
            setDelimiter("|"); // Define o delimitador
            setNames(resultadoCampos.stream().map(String::toLowerCase).toArray(String[]::new)); // Define os nomes das colunas em maiúsculas
        }});

        lineMapper.setFieldSetMapper(new FieldSetMapper<Map<String, Object>>() {
            @Override
            public Map<String, Object> mapFieldSet(FieldSet fieldSet) {
                Map<String, Object> map = new HashMap<>();
                for (String name : fieldSet.getNames()) {
                    map.put(name, fieldSet.readString(name)); // Lê como String
                }
                return map;
            }
        });

        //lineMapper.setStrict(true); // Opcional: Se você quiser garantir que todas as colunas sejam mapeadas
        reader.setLineMapper(lineMapper);
        reader.setLinesToSkip(0); // Se precisar ignorar o cabeçalho

        return reader;
    }



    @Bean
    @JobScope
    public ItemWriter<Map<String, Object>> writer(@Qualifier("springDS") DataSource dataSource, @Value("#{jobExecution}") JobExecution jobExecution) {
        JobParameters jobParameters = jobExecution.getJobParameters();

        return items -> {
            try {
                NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

                // String SQL correta, utilizando nomes de parâmetros que correspondem às chaves do Map
                String sql = "INSERT INTO TB_PESSOA (nome, email, data_nascimento, idade) VALUES (:nome, :email, :data_nascimento, :idade)";

                for (Map<String, Object> item : items) {
                    // Crie um MapSqlParameterSource a partir do item
                    MapSqlParameterSource params = new MapSqlParameterSource(item);

                    // Debug: imprime os parâmetros que serão usados na inserção
                    System.out.println("****************Parâmetros: " + params.getValues());

                    // Execute a inserção
                    namedParameterJdbcTemplate.update(sql, params);
                }
            } catch (Exception e) {
                throw new RuntimeException("Erro ao gravar itens: " + e.getMessage(), e);
            }
        };
    }

    @Bean
    public Step moverArquivoStep(JobRepository jobRepository){
        return new StepBuilder("moverArquivo", jobRepository)
                .tasklet(moverArquivo(), transactionManager)
                .build();
    }

    @Bean
    public Tasklet moverArquivo(){
        return (contribution, chunkContext) -> {
            File pastaOrigem = new File("carga");
            File pastaDestino = new File("imported-files");

            if(!pastaDestino.exists()){
                pastaDestino.mkdirs();
            }

            File[] arquivos = pastaOrigem.listFiles(((dir, name) -> name.endsWith(".csv")));

            if (arquivos != null){

                for(File arquivo:arquivos){

                    File arquivoDestino = new File(pastaDestino, arquivo.getName());

                    if(arquivo.renameTo(arquivoDestino)){
                        log.info("Arquivo movido: " + arquivo.getName());
                    }else{
                        throw new RuntimeException("Não foi possível mover o Arquivo " + arquivo.getName());
                    }
                }
            }
            return  RepeatStatus.FINISHED;
        };
    }

//---------------------------------------FIM MOTOR---------------------------------------------
    @Bean
    public JobExecutionListener tempoExecucaoJob() {
        return new JobExecutionUtils();
    }

}
