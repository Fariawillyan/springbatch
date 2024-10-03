package br.com.teste.cargamaster.domain.motor;

import br.com.teste.cargamaster.domain.app.Cliente1.Pessoa;
import br.com.teste.cargamaster.domain.app.Fabricabusiness;
import br.com.teste.cargamaster.domain.app.FabricaCliente;
import br.com.teste.cargamaster.domain.motor.PreJob.entity.CargaCampo;
import br.com.teste.cargamaster.domain.motor.Utils.JobExecutionUtils;
import br.com.teste.cargamaster.domain.motor.Utils.JobStatus;
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
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

import javax.sql.DataSource;
import java.util.stream.Collectors;

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
    public Step step(ItemReader<Object> reader, ItemWriter<Object> writer, JobRepository jobRepository){
        return new StepBuilder("step", jobRepository)
                .<Object, Object>chunk(200, transactionManager)
                .reader(reader)
                //.processor(... para processar algo se precisar)
                .writer(writer)
                .build();
    }

    @Bean
    @JobScope
    public FlatFileItemReader<Object> reader(@Value("#{jobExecution}") JobExecution jobExecution){

        JobParameters jobParameters = jobExecution.getJobParameters();

        Fabricabusiness fabricabusiness = new Fabricabusiness();
        Object cliente = fabricabusiness.buscaClienteParaCarga(jobParameters);

        final List<String> resultadoCampos = fabricabusiness.gerarCamposParaProcessamento(jobParameters);

        FlatFileItemReaderBuilder<Object> readerBuilder = new FlatFileItemReaderBuilder<>()
                .name("reader")
                .resource(new FileSystemResource("carga/pessoas.csv")) //Se for testar posicionamento mudar o arquivo e o usaPosicionamento para true
                .comments("--")
                .targetType((Class<Object>) cliente.getClass());

        if (Objects.requireNonNull(jobParameters.getString("usaPosicionamento")).equalsIgnoreCase("true")) {
            readerBuilder.lineTokenizer(new FixedLengthTokenizer() {{
                setNames(resultadoCampos.toArray(new String[0]));
                setColumns(
                        new Range(1, 5),
                        new Range(6, 25),
                        new Range(26, 45),
                        new Range(46, 48),
                        new Range(49, 49)
                );
            }});
        } else {
            readerBuilder.delimited()
                    .delimiter(Objects.requireNonNull(jobParameters.getString("delimitador")))
                    .names(resultadoCampos.toArray(new String[0]));
        }

        return readerBuilder.build();
    }


    @Bean
    @JobScope
    //@StepScope caso precisar ter multiplos steps.
    public ItemWriter<Object> writer(@Qualifier("springDS") DataSource dataSource,  @Value("#{jobExecution}") JobExecution jobExecution) {

        JobParameters jobParameters = jobExecution.getJobParameters();

        return new JdbcBatchItemWriterBuilder<Object>()
                .dataSource(dataSource)
                .sql(Objects.requireNonNull(jobParameters.getString("cargaDestinoSqlInsert")))
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
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