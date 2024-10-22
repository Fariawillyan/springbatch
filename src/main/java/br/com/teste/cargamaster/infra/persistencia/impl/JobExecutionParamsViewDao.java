package br.com.teste.cargamaster.infra.persistencia.impl;

import br.com.teste.cargamaster.app.rest.entity.JobExecutionParamsView;
import br.com.teste.cargamaster.infra.persistencia.IJobExecutionParamsViewDao;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobExecutionParamsViewDao implements IJobExecutionParamsViewDao {

    private final JdbcClient jdbcClient;

    public JobExecutionParamsViewDao(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<JobExecutionParamsView> buscarJobExecutionParams() {
        return jdbcClient.sql("""
                        SELECT 
                        *
                        FROM
                            batch_job_execution_params;                        
                        """)
                .query(JobExecutionParamsView.class)
                .list();
    }
}
