package com.ktmmobile.msf.external.mybatis.config;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.ktmmobile.msf.commons.common.datasource.smartform.SmartFormDataSourceConfig;

@Configuration(proxyBeanMethods = false)
public class SmartFormMyBatisConfig extends MyBatisConfigSupport {

    public static final String SQL_SESSION_FACTORY = "smartFormSqlSessionFactory";

    public SmartFormMyBatisConfig(
        MyBatisCustomProperties properties,
        ObjectProvider<Interceptor[]> interceptorsProvider
    ) {
        super(properties, interceptorsProvider);
    }

    @Bean
    public static MapperScannerConfigurer smartFormMapperScannerConfigurer(Environment environment) {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage(String.join(",", Binder.get(environment)
            .bind("mybatis.datasource.smartform.mapper-scan-packages", Bindable.listOf(String.class))
            .orElseThrow(() -> new IllegalStateException("mybatis.datasource.smartform.mapper-scan-packages must be configured"))));
        configurer.setSqlSessionFactoryBeanName(SQL_SESSION_FACTORY);
        return configurer;
    }

    @Bean(SQL_SESSION_FACTORY)
    public SqlSessionFactory smartFormSqlSessionFactory(
        @Qualifier(SmartFormDataSourceConfig.SMARTFORM_DATASOURCE) DataSource dataSource,
        MyBatisCustomProperties properties
    ) throws Exception {
        return createSqlSessionFactory(dataSource, properties.smartformMapperLocations());
    }

    @Bean("sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(
        @Qualifier(SQL_SESSION_FACTORY) SqlSessionFactory sqlSessionFactory
    ) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
