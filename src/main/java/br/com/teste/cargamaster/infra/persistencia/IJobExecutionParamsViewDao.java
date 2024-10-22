package br.com.teste.cargamaster.infra.persistencia;

import br.com.teste.cargamaster.app.rest.entity.JobExecutionParamsView;
import br.com.teste.cargamaster.app.rest.entity.JobExecutionView;

import java.util.List;

public interface IJobExecutionParamsViewDao {
    List<JobExecutionParamsView> buscarJobExecutionParams();
}
