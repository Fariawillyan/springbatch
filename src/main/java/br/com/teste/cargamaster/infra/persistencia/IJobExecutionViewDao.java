package br.com.teste.cargamaster.infra.persistencia;

import br.com.teste.cargamaster.app.rest.entity.JobExecutionView;

import java.util.List;

public interface IJobExecutionViewDao {
    List<JobExecutionView> buscarJobExecution();
}
