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

import com.ktmmobile.msf.commons.common.datasource.mcp.McpDataSourceConfig;

@Configuration(proxyBeanMethods = false)
public class McpMyBatisConfig extends MyBatisConfigSupport {

    public static final String SQL_SESSION_FACTORY = "mcpSqlSessionFactory";

    public McpMyBatisConfig(
        MyBatisCustomProperties properties,
        ObjectProvider<Interceptor[]> interceptorsProvider
    ) {
        super(properties, interceptorsProvider);
    }

    @Bean
    public static MapperScannerConfigurer mcpMapperScannerConfigurer(Environment environment) {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage(String.join(",", Binder.get(environment)
            .bind("mybatis.datasource.mcp.mapper-scan-packages", Bindable.listOf(String.class))
            .orElseThrow(() -> new IllegalStateException("mybatis.datasource.mcp.mapper-scan-packages must be configured"))));
        configurer.setSqlSessionFactoryBeanName(SQL_SESSION_FACTORY);
        return configurer;
    }

    @Bean(SQL_SESSION_FACTORY)
    public SqlSessionFactory mcpSqlSessionFactory(
        @Qualifier(McpDataSourceConfig.MCP_DATASOURCE) DataSource dataSource,
        MyBatisCustomProperties properties
    ) throws Exception {
        return createSqlSessionFactory(dataSource, properties.mcpMapperLocations());
    }

    @Bean("mcpSqlSession")
    public SqlSessionTemplate mcpSqlSession(
        @Qualifier(SQL_SESSION_FACTORY) SqlSessionFactory sqlSessionFactory
    ) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
