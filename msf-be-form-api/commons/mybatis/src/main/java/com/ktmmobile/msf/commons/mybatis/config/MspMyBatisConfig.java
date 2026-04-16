package com.ktmmobile.msf.commons.mybatis.config;

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

import com.ktmmobile.msf.commons.common.datasource.msp.MspDataSourceConfig;

@Configuration(proxyBeanMethods = false)
public class MspMyBatisConfig extends MyBatisConfigSupport {

    public static final String SQL_SESSION_FACTORY = "mspSqlSessionFactory";

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
    ) throws Exception {
        return createSqlSessionFactory(dataSource, properties.mspMapperLocations());
    }

    @Bean("sqlSession2")
    public SqlSessionTemplate sqlSession2(
        @Qualifier(SQL_SESSION_FACTORY) SqlSessionFactory sqlSessionFactory
    ) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
