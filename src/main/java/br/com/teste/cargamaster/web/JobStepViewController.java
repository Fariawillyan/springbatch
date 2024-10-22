package br.com.teste.cargamaster.web;

import br.com.teste.cargamaster.app.rest.entity.JobStepView;
import br.com.teste.cargamaster.app.rest.service.IJobStepViewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("/rest/job/info")
public class JobStepViewController {

    private IJobStepViewService iJobStepViewService;

    public JobStepViewController(IJobStepViewService iJobStepViewService) {
        this.iJobStepViewService = iJobStepViewService;
    }

    @GetMapping("/step")
    private List<JobStepView> jobStepViewsList(){
        return iJobStepViewService.listarJobStep();
    }
}
