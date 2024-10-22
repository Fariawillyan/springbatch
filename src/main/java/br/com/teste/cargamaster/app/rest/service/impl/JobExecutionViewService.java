package br.com.teste.cargamaster.app.rest.service.impl;

import br.com.teste.cargamaster.app.rest.entity.JobExecutionView;
import br.com.teste.cargamaster.app.rest.service.IJobExecutionViewService;
import br.com.teste.cargamaster.infra.persistencia.IJobExecutionViewDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobExecutionViewService implements IJobExecutionViewService {
    private IJobExecutionViewDao iJobExecutionViewDao;

    public JobExecutionViewService(IJobExecutionViewDao iJobExecutionViewDao) {
        this.iJobExecutionViewDao = iJobExecutionViewDao;
    }

    @Override
    public List<JobExecutionView> listarJobExecution() {
        return iJobExecutionViewDao.buscarJobExecution();
    }

}
