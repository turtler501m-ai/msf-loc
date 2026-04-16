package com.ktmmobile.msf.commons.mybatis.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

abstract class MyBatisConfigSupport {

    private static final PathMatchingResourcePatternResolver RESOURCE_RESOLVER =
        new PathMatchingResourcePatternResolver();

    private final MyBatisCustomProperties properties;
    private final ObjectProvider<Interceptor[]> interceptorsProvider;

    protected MyBatisConfigSupport(
        MyBatisCustomProperties properties,
        ObjectProvider<Interceptor[]> interceptorsProvider
    ) {
        this.properties = properties;
        this.interceptorsProvider = interceptorsProvider;
    }

    protected SqlSessionFactory createSqlSessionFactory(
        DataSource dataSource,
        List<String> mapperLocationPatterns
    ) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTypeAliasesPackage(properties.getTypeAliasesPackage());
        factoryBean.setTypeHandlersPackage(properties.getTypeHandlersPackage());
        factoryBean.setConfiguration(defaultConfiguration());

        Interceptor[] interceptors = interceptorsProvider.getIfAvailable();
        if (interceptors != null && interceptors.length > 0) {
            factoryBean.setPlugins(interceptors);
        }

        Resource[] mapperLocations = resolveMapperLocations(mapperLocationPatterns);
        if (mapperLocations.length > 0) {
            factoryBean.setMapperLocations(mapperLocations);
        }

        return factoryBean.getObject();
    }

    private Configuration defaultConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setAutoMappingBehavior(AutoMappingBehavior.FULL);
        Properties variables = new Properties();
        variables.setProperty("org.apache.ibatis.parsing.PropertyParser.enable-default-value", "true");
        configuration.setVariables(variables);
        return configuration;
    }

    private Resource[] resolveMapperLocations(List<String> mapperLocationPatterns) throws IOException {
        List<Resource> resources = new ArrayList<>();
        for (String pattern: mapperLocationPatterns) {
            Resource[] matchedResources = RESOURCE_RESOLVER.getResources(pattern);
            for (Resource resource: matchedResources) {
                resources.add(resource);
            }
        }
        return resources.toArray(Resource[]::new);
    }
}
