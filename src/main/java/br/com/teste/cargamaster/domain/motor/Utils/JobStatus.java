package br.com.teste.cargamaster.domain.motor.Utils;

import br.com.teste.cargamaster.domain.motor.PosJob.IDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobStatus {

    private final IDispatcher posJob;

    public JobStatus(IDispatcher posJob) {
        this.posJob = posJob;
    }

    public void EnvioStatusParaPosJob(BatchStatus status) {
        switch (status) {
            case COMPLETED -> {

                log.info("█ █ █ JOB FINALIZADO COM SUCESSO █ █ █");

                posJob.processaEmailService();
            }
            case FAILED -> {
                log.error("█ █ █ JOB FALHOU █ █ █");
                // SE QUISER PODERA ENVIAR PARA FILA
            }
            case STOPPED -> {
                log.warn("█ █ █ JOB PARADO █ █ █");
            }
            case STARTING -> {
                log.info("█ █ █ JOB INICIANDO █ █ █");
            }
            case STARTED -> {
                log.info("█ █ █ JOB EM EXECUÇÃO █ █ █");
            }
            case UNKNOWN -> {
                log.warn("█ █ █ STATUS DESCONHECIDO █ █ █");
            }
            default -> {
                log.error("█ █ █ STATUS NÃO TRATADO █ █ █");
                // fazer alguma coisa
            }
        }
    }

    private void dispacharNotificacao(){
        posJob.processaEmailService();
    }
}
