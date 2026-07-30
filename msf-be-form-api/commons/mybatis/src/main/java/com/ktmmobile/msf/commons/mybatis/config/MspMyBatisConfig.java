package com.ktmmobile.msf.commons.mybatis.config;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.ktmmobile.msf.commons.common.datasource.msp.MspDataSourceConfig;
import com.ktmmobile.msf.commons.mybatis.interceptor.AutoAuditingInterceptor;

@Configuration(proxyBeanMethods = false)
public class MspMyBatisConfig extends MyBatisConfigSupport {

    public static final String SQL_SESSION_FACTORY = "mspSqlSessionFactory";
    public static final String SQL_SESSION_TEMPLATE = "mspSqlSessionTemplate";
    public static final String BATCH_SQL_SESSION_TEMPLATE = "mspBatchSqlSessionTemplate";

    public MspMyBatisConfig(
        MyBatisCustomProperties properties,
        ObjectProvider<Interceptor[]> interceptorsProvider
    ) {
        super(properties, interceptorsProvider);
    }

    @Bean
    public static MapperScannerConfigurer mspMapperScannerConfigurer(Environment environment) {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage(String.join(",", Binder.get(environment)
            .bind("mybatis.datasource.msp.mapper-scan-packages", Bindable.listOf(String.class))
            .orElseThrow(() -> new IllegalStateException("mybatis.datasource.msp.mapper-scan-packages must be configured"))));
        configurer.setSqlSessionFactoryBeanName(SQL_SESSION_FACTORY);
        return configurer;
    }

    @Bean(SQL_SESSION_FACTORY)
    public SqlSessionFactory mspSqlSessionFactory(
        @Qualifier(MspDataSourceConfig.MSP_DATASOURCE) DataSource dataSource,
        MyBatisCustomProperties properties
    ) {
        return createSqlSessionFactory(dataSource, properties.mspMapperLocations(),
            interceptor -> !(interceptor instanceof AutoAuditingInterceptor),  // AutoAuditingInterceptor 미적용
            configuration -> configuration.setJdbcTypeForNull(JdbcType.NULL)
        );
    }

    @Bean(SQL_SESSION_TEMPLATE)
    public SqlSessionTemplate mspSqlSessionTemplate(
        @Qualifier(SQL_SESSION_FACTORY) SqlSessionFactory sqlSessionFactory
    ) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(BATCH_SQL_SESSION_TEMPLATE)
    public SqlSessionTemplate mspBatchSqlSessionTemplate(
        @Qualifier(SQL_SESSION_FACTORY) SqlSessionFactory sqlSessionFactory
    ) {
        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
    }
}
