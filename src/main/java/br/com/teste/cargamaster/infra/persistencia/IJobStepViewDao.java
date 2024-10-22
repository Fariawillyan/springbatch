package br.com.teste.cargamaster.infra.persistencia;

import br.com.teste.cargamaster.app.rest.entity.JobStepView;

import java.util.List;

public interface IJobStepViewDao {
    List<JobStepView> buscarJobStepView();
}
