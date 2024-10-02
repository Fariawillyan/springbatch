package br.com.teste.cargamaster.domain.motor;

import br.com.teste.cargamaster.domain.motor.PreJob.entity.Pessoa;
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
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

import javax.sql.DataSource;
import java.util.Objects;

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
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step step(ItemReader<Pessoa> reader, ItemWriter<Pessoa> writer, JobRepository jobRepository){
        return new StepBuilder("step", jobRepository)
                .<Pessoa, Pessoa>chunk(200, transactionManager)
                .reader(reader)
                //.processor(... para processar algo se precisar)
                .writer(writer)
                .build();
    }

    @Bean
    @JobScope
    public FlatFileItemReader<Pessoa> reader(@Value("#{jobExecution}") JobExecution jobExecution){

        JobParameters jobParameters = jobExecution.getJobParameters();

        FlatFileItemReaderBuilder<Pessoa> readerBuilder = new FlatFileItemReaderBuilder<Pessoa>()
                .name("reader")
                .resource(new FileSystemResource("carga/pessoas.csv")) //Se for testar posicionamento mudar o arquivo e o usaPosicionamento para true
                .comments("--")
                .targetType(Pessoa.class);

        if (Objects.requireNonNull(jobParameters.getString("usaPosicionamento")).equalsIgnoreCase("true")) {
            readerBuilder.lineTokenizer(new FixedLengthTokenizer() {{
                setNames("nome", "email", "dataNascimento", "idade", "id");
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
                    .names("nome", "email", "dataNascimento", "idade");
        }

        return readerBuilder.build();
    }

    @Bean
    @JobScope
    //@StepScope caso precisar ter multiplos steps.
    public ItemWriter<Pessoa> writer(@Qualifier("springDS") DataSource dataSource,  @Value("#{jobExecution}") JobExecution jobExecution) {

        JobParameters jobParameters = jobExecution.getJobParameters();

        return new JdbcBatchItemWriterBuilder<Pessoa>()
                .dataSource(dataSource)
                .sql(Objects.requireNonNull(jobParameters.getString("cargaDestinoSqlInsert")))
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }

//---------------------------------------FIM MOTOR---------------------------------------------
    @Bean
    public JobExecutionListener tempoExecucaoJob() {
        return new JobExecutionUtils();
    }
}