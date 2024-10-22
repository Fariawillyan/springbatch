package br.com.teste.cargamaster.app.rest.service.impl;

import br.com.teste.cargamaster.app.rest.entity.JobExecutionParamsView;
import br.com.teste.cargamaster.app.rest.service.IJobExecutionParamsViewService;
import br.com.teste.cargamaster.infra.persistencia.IJobExecutionParamsViewDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobExecutionParamsViewService implements IJobExecutionParamsViewService {

    private IJobExecutionParamsViewDao iJobExecutionParamsViewDao;

    public JobExecutionParamsViewService(IJobExecutionParamsViewDao iJobExecutionParamsViewDao) {
        this.iJobExecutionParamsViewDao = iJobExecutionParamsViewDao;
    }

    @Override
    public List<JobExecutionParamsView> listarJobExecution() {
        return iJobExecutionParamsViewDao.buscarJobExecutionParams();
    }
}
