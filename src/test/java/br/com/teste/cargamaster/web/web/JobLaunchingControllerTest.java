package br.com.teste.cargamaster.web.web;

import br.com.teste.cargamaster.app.motor.preJob.PreJobImpl;
import br.com.teste.cargamaster.app.motor.preJob.vo.ExecutarPreJob;
import br.com.teste.cargamaster.web.JobLaunchingController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

class JobLaunchingControllerTest {

    @InjectMocks
    private JobLaunchingController jobLaunchingController;

    @Mock
    private PreJobImpl preJob;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRunJobMeta_DadosInvalidos(){

        ExecutarPreJob executarPreJob = new ExecutarPreJob();
        executarPreJob.setIdCarga(0);
        executarPreJob.setEmsNumero(0);

        ResponseEntity<Object> response = jobLaunchingController.runJob(executarPreJob);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testRunJobMeta_DadosValidos(){

        ExecutarPreJob executarPreJob = new ExecutarPreJob();
        executarPreJob.setIdCarga(2);
        executarPreJob.setEmsNumero(3);

        ResponseEntity<Object> response = jobLaunchingController.runJob(executarPreJob);

        verify(preJob).executar(executarPreJob);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRunJobMeta_LancarExcecao_Conflit(){

        ExecutarPreJob executarPreJob = new ExecutarPreJob();
        executarPreJob.setIdCarga(1);
        executarPreJob.setEmsNumero(1);

        doThrow(new IllegalStateException("Job já em execução")).when(preJob).executar(executarPreJob);
        ResponseEntity<Object> response = jobLaunchingController.runJob(executarPreJob);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void testRunJobMeta_LancarExcecao_BadRequest(){

        ExecutarPreJob executarPreJob = new ExecutarPreJob();
        executarPreJob.setIdCarga(6);
        executarPreJob.setEmsNumero(7);

        doThrow(new RuntimeException("ERRO NO SERVIDOR")).when(preJob).executar(executarPreJob);
        ResponseEntity<Object> response = jobLaunchingController.runJob(executarPreJob);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}