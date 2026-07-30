package com.ktmmobile.msf.commons.common.lock.postgres;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ktmmobile.msf.commons.common.datasource.smartform.SmartFormDataSourceConfig;

/**
 * PostgreSQL advisory transaction lock 구성
 */
@Configuration(proxyBeanMethods = false)
public class PostgresAdvisoryLockConfig {

    /**
     * SmartForm 데이터소스용 PostgreSQL advisory lock manager
     */
    @Primary
    @Bean
    public PostgresAdvisoryLockManager smartFormPostgresAdvisoryLockManager(
        @Qualifier(SmartFormDataSourceConfig.SMARTFORM_DATASOURCE) DataSource dataSource
    ) {
        return new DefaultPostgresAdvisoryLockManager(new JdbcTemplate(dataSource));
    }
}
