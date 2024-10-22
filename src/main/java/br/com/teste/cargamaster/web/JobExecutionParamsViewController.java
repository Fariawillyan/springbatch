package br.com.teste.cargamaster.web;

import br.com.teste.cargamaster.app.rest.entity.JobExecutionParamsView;
import br.com.teste.cargamaster.app.rest.service.IJobExecutionParamsViewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/job/info")
public class JobExecutionParamsViewController {
    private IJobExecutionParamsViewService iJobExecutionViewService;

    public JobExecutionParamsViewController(IJobExecutionParamsViewService iJobExecutionViewService) {
        this.iJobExecutionViewService = iJobExecutionViewService;
    }

    @GetMapping("/execution/params")
    private List<JobExecutionParamsView> JobExecutionParamsView(){
        return  iJobExecutionViewService.listarJobExecution();
    }

}
