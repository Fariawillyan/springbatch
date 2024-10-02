package br.com.teste.cargamaster.domain.motor.PreJob;

import br.com.teste.cargamaster.domain.motor.PreJob.vo.ExecutarPreJob;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;


public interface IPreJob {
    void executar(ExecutarPreJob executarPreJob) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException;
}
