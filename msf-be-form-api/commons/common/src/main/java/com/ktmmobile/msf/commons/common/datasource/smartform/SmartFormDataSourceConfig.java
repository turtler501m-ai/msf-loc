package com.ktmmobile.msf.commons.common.datasource.smartform;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.ktmmobile.msf.commons.common.datasource.common.AbstractGroupDataSourceSupport;
import com.ktmmobile.msf.commons.common.datasource.common.property.DataSourcesProperties;

@Configuration(proxyBeanMethods = false)
public class SmartFormDataSourceConfig extends AbstractGroupDataSourceSupport {

    public static final String GROUP_NAME = "smartform";
    public static final String SMARTFORM_DATASOURCE = GROUP_NAME + DATASOURCE_BEAN;
    public static final String SMARTFORM_TX_MANAGER = GROUP_NAME + TX_MANAGER_BEAN;

    public SmartFormDataSourceConfig(DataSourcesProperties dataSourcesProperties, Environment environment) {
        super(dataSourcesProperties, environment);
    }

    @Primary
    @Bean(SMARTFORM_DATASOURCE)
    public DataSource dataSource() {
        return createRoutingDataSource(GROUP_NAME);
    }

    @Primary
    @Bean(SMARTFORM_TX_MANAGER)
    public PlatformTransactionManager transactionManager(
        @Qualifier(SMARTFORM_DATASOURCE) DataSource dataSource
    ) {
        return new JdbcTransactionManager(dataSource);
    }
}
