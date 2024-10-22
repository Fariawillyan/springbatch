package br.com.teste.cargamaster.app.rest.service.impl;

import br.com.teste.cargamaster.app.rest.entity.JobStepView;
import br.com.teste.cargamaster.app.rest.service.IJobStepViewService;
import br.com.teste.cargamaster.infra.persistencia.IJobStepViewDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobStepViewService implements IJobStepViewService {

    private final IJobStepViewDao iJobStepViewDao;

    public JobStepViewService(IJobStepViewDao iJobStepViewDao) {
        this.iJobStepViewDao = iJobStepViewDao;
    }

    @Override
    public List<JobStepView> listarJobStep() {
        return iJobStepViewDao.buscarJobStepView();
    }
}
