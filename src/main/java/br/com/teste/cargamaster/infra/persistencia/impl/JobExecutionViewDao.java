package br.com.teste.cargamaster.infra.persistencia.impl;

import br.com.teste.cargamaster.app.rest.entity.JobExecutionView;
import br.com.teste.cargamaster.infra.persistencia.IJobExecutionViewDao;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobExecutionViewDao implements IJobExecutionViewDao {

    private final JdbcClient jdbcClient;

    public JobExecutionViewDao(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<JobExecutionView> buscarJobExecution() {
        return jdbcClient.sql("""
                        SELECT
                        *
                        FROM
                            batch_job_execution;                       
                        """)
                .query(JobExecutionView.class)
                .list();
    }
}
