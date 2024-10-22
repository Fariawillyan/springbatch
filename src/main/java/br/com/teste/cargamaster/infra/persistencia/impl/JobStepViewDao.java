package br.com.teste.cargamaster.infra.persistencia.impl;

import br.com.teste.cargamaster.app.rest.entity.JobExecutionView;
import br.com.teste.cargamaster.app.rest.entity.JobStepView;
import br.com.teste.cargamaster.infra.persistencia.IJobStepViewDao;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobStepViewDao implements IJobStepViewDao {

    private JdbcClient jdbcClient;

    public JobStepViewDao(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<JobStepView> buscarJobStepView() {
        return jdbcClient.sql("""
                        SELECT
                        *
                        FROM
                            batch_step_execution;
                        """)
                .query(JobStepView.class)
                .list();
    }
}
