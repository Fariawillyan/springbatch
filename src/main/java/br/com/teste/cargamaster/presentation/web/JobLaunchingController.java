package br.com.teste.cargamaster.presentation.web;

import br.com.teste.cargamaster.domain.motor.PosJob.IDispatcher;
import br.com.teste.cargamaster.domain.motor.PreJob.IPreJob;
import br.com.teste.cargamaster.domain.motor.PreJob.vo.ExecutarPreJob;
//import br.com.teste.cargamaster.infra.message.fila.Producer;
import br.com.teste.cargamaster.domain.motor.exception.CargaMasterException;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teste/")
public class JobLaunchingController {
    private final JobLauncher jobLauncher;
    private final  Job job;
    private final IDispatcher message;
    private final IPreJob preJob;

    public JobLaunchingController(JobLauncher jobLauncher, Job job, IDispatcher message, IPreJob preJob) {
        this.jobLauncher = jobLauncher;
        this.job = job;
        this.message = message;
        this.preJob = preJob;
    }

    /*
    @Autowired
    private Producer producer;
*/

    //WIP
    //testar com ems = 2 e carga = 3 no swagger para ver a mágica.
    @PostMapping(path = "/run/meta")
    public ResponseEntity<Object> runJobMeta(@RequestBody ExecutarPreJob executarPreJob) {
        try {

            if (executarPreJob.getEmsNumero() <= 0 || executarPreJob.getIdCarga() <= 0) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CargaMasterException("Dados Invalidos"));
            }

            preJob.executar(executarPreJob);
            return ResponseEntity.status(HttpStatus.OK).body("Job inicializado com sucesso!");

        } catch (IllegalStateException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Job com este parâmetro já está em execução");
        } catch (Exception e) {
            e.printStackTrace();
            //producer.tryAgain(executarPreJob);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Não foi possível iniciar o Job. Enviando para Fila executar novamente mais tarde ");
    }
}
