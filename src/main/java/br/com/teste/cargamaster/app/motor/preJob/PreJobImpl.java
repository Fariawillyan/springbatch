package br.com.teste.cargamaster.app.motor.preJob;

import br.com.teste.cargamaster.app.motor.MainJob;
import br.com.teste.cargamaster.app.motor.preJob.vo.PreJobResultado;
import br.com.teste.cargamaster.app.motor.preJob.vo.ExecutarPreJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class PreJobImpl implements IPreJob{

    private MontarPreJob montarPreJob;
    private MainJob mainJob;

    public PreJobImpl(MontarPreJob montarPreJob, MainJob mainJob) {
        this.montarPreJob = montarPreJob;
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

        PreJobResultado preJobMontado = montarPreJob.montarPreJob(executarPreJob);
        log.info(" Iniciando preJobMontado :" + preJobMontado.toString());

        JobParameters jobParameters = montarPreJob.resultado(preJobMontado);
        log.info(" Inciando parametros para execucao :" + jobParameters);

        return jobParameters;
    }
}
