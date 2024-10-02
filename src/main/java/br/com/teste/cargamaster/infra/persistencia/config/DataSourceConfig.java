package br.com.teste.cargamaster.infra.persistencia.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource springDS(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    public PlatformTransactionManager transactionManagerApp(@Qualifier("springDS") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}