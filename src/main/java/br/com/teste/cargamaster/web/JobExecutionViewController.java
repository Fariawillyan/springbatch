package br.com.teste.cargamaster.web;


import br.com.teste.cargamaster.app.rest.entity.JobExecutionView;
import br.com.teste.cargamaster.app.rest.service.IJobExecutionViewService;
import br.com.teste.cargamaster.app.rest.service.impl.JobExecutionViewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/job/info")
public class JobExecutionViewController {
    private IJobExecutionViewService iJobExecutionViewService;

    public JobExecutionViewController(JobExecutionViewService jobExecutionViewService) {
        this.iJobExecutionViewService = jobExecutionViewService;
    }

    @GetMapping("/execution")
    private List<JobExecutionView> jobExecutionViewList(){
        return  iJobExecutionViewService.listarJobExecution();
    }

}

