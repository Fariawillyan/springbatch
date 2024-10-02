package br.com.teste.cargamaster.domain.motor.PreJob;

import br.com.teste.cargamaster.domain.motor.MainJob;
import br.com.teste.cargamaster.domain.motor.PreJob.vo.PreJobResultado;
import br.com.teste.cargamaster.domain.motor.PreJob.vo.ExecutarPreJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class PreJobImpl implements IPreJob{

    private PreJobFacade preJobFacade;
    private MainJob mainJob;

    public PreJobImpl(PreJobFacade preJobFacade, MainJob mainJob) {
        this.preJobFacade = preJobFacade;
        this.mainJob = mainJob;
    }

    public void executar (ExecutarPreJob executarPreJob)  {
        try {

            JobParameters preJobResultado = montarPreJob(executarPreJob);

            mainJob.executeJobMain(preJobResultado);

        } catch (Exception e) {
            e.getMessage();
        }
    }

    private JobParameters montarPreJob(ExecutarPreJob executarPreJob){

        PreJobResultado preJobMontado = preJobFacade.montarPreJob(executarPreJob);
        log.info(" Iniciando preJobMontado :" + preJobMontado.toString());

        JobParameters jobParameters = preJobFacade.resultado(preJobMontado);
        log.info(" Inciando parametros para execucao :" + jobParameters);

        return jobParameters;
    }
}
