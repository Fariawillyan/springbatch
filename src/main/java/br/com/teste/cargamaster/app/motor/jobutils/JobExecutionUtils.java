package br.com.teste.cargamaster.app.motor.jobutils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class JobExecutionUtils implements JobExecutionListener {

    private long horaInicioJob;
    private long horaFimJob;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        horaInicioJob = System.currentTimeMillis();
        logUsoDeMemoria("--------------------> Antes do Job");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        horaFimJob = System.currentTimeMillis();
        long duration = horaFimJob - horaInicioJob;

        Long jobId = jobExecution.getJobId();
        Long jobExecutionId = jobExecution.getId();

        log.info("JOB ID: {}, Job Execution ID: {}", jobId, jobExecutionId);

       log.info("Nome da Thread: {}", Thread.currentThread().getName());

 /*      //CASO FOR TESTAR VARIOS JOBS HABILITAR ESSE SLEEP
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
*/
        log.info("_____________________________ JOB EXECUTADO EM : " + duration + " milliseconds. __________________________");

        logUsoDeMemoria("--------------------> Depois do Job");
    }

    private void logUsoDeMemoria(String phase) {

        Runtime runtime = Runtime.getRuntime();
        long memoriaUsada = runtime.totalMemory() - runtime.freeMemory();
        long memoriaMax = runtime.maxMemory();
        long memoriaLivre = runtime.freeMemory();

        log.info("{} - Memoria Usada: {} MB, Memoria Livre: {} MB, Maximo Memoria: {} MB <-----------------------",
                phase, memoriaUsada / (1024 * 1024), memoriaLivre / (1024 * 1024), memoriaMax / (1024 * 1024));
    }
}
