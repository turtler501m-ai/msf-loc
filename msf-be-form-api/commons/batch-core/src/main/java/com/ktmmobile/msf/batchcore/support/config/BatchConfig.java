package com.ktmmobile.msf.batchcore.support.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JdbcDefaultBatchConfiguration;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.ktmmobile.msf.batchcore.support.properties.BatchCoreProperties;
import com.ktmmobile.msf.commons.common.datasource.smartform.SmartFormDataSourceConfig;

@Configuration(proxyBeanMethods = false)
public class BatchConfig extends JdbcDefaultBatchConfiguration {

    private final DataSource dataSource;
    private final PlatformTransactionManager transactionManager;
    private final ObjectProvider<AsyncTaskExecutor> asyncTaskExecutorProvider;
    private final BatchCoreProperties batchCoreProperties;

    public BatchConfig(
        @Qualifier(SmartFormDataSourceConfig.SMARTFORM_DATASOURCE) DataSource dataSource,
        @Qualifier(SmartFormDataSourceConfig.SMARTFORM_TX_MANAGER) PlatformTransactionManager transactionManager,
        ObjectProvider<AsyncTaskExecutor> asyncTaskExecutorProvider,
        BatchCoreProperties batchCoreProperties
    ) {
        this.dataSource = dataSource;
        this.transactionManager = transactionManager;
        this.asyncTaskExecutorProvider = asyncTaskExecutorProvider;
        this.batchCoreProperties = batchCoreProperties;
    }

    @Override
    protected DataSource getDataSource() {
        return dataSource;
    }

    @Override
    protected PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    @Override
    protected TaskExecutor getTaskExecutor() {
        AsyncTaskExecutor asyncTaskExecutor = asyncTaskExecutorProvider.getIfAvailable();
        if (asyncTaskExecutor != null) {
            return asyncTaskExecutor;
        }
        return super.getTaskExecutor();
    }

    @Override
    protected String getTablePrefix() {
        return batchCoreProperties.jdbc().tablePrefix();
    }

    @Bean
    public JobRegistry jobRegistry() {
        return new MapJobRegistry();
    }
}
