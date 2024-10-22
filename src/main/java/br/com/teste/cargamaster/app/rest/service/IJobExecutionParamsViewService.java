package br.com.teste.cargamaster.app.rest.service;

import br.com.teste.cargamaster.app.rest.entity.JobExecutionParamsView;

import java.util.List;

public interface IJobExecutionParamsViewService {
    List<JobExecutionParamsView> listarJobExecution();
}
