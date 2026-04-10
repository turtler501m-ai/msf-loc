package com.ktmmobile.msf.commons.common.datasource.msp;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.ktmmobile.msf.commons.common.datasource.common.AbstractGroupDataSourceSupport;
import com.ktmmobile.msf.commons.common.datasource.common.property.DataSourcesProperties;

@Configuration(proxyBeanMethods = false)
public class MspDataSourceConfig extends AbstractGroupDataSourceSupport {

    public static final String GROUP_NAME = "msp";
    public static final String MSP_DATASOURCE = GROUP_NAME + DATASOURCE_BEAN;
    public static final String MSP_TX_MANAGER = GROUP_NAME + TX_MANAGER_BEAN;

    public MspDataSourceConfig(DataSourcesProperties dataSourcesProperties, Environment environment) {
        super(dataSourcesProperties, environment);
    }

    @Bean(MSP_DATASOURCE)
    public DataSource dataSource() {
        return createStandaloneDataSource(GROUP_NAME);
    }

    @Bean(MSP_TX_MANAGER)
    public PlatformTransactionManager transactionManager(
        @Qualifier(MSP_DATASOURCE) DataSource dataSource
    ) {
        return new JdbcTransactionManager(dataSource);
    }
}
