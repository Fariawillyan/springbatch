package br.com.teste.cargamaster.app.rest.service;

import br.com.teste.cargamaster.app.rest.entity.JobExecutionView;

import java.util.List;

public interface IJobExecutionViewService {
    List<JobExecutionView> listarJobExecution();

}
