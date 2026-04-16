package com.ktmmobile.msf.commons.mybatis.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ktmmobile.msf.commons.mybatis.interceptor.QueryIdGenerationInterceptor;
import com.ktmmobile.msf.commons.mybatis.interceptor.WithAuditingParameterInterceptor;

@Slf4j
@RequiredArgsConstructor
@EnableAutoConfiguration(exclude = MybatisAutoConfiguration.class)
@Configuration(proxyBeanMethods = false)
public class MyBatisCommonConfig {

    private final MyBatisCustomProperties properties;

    @Bean
    public Interceptor withAuditingParameterInterceptor() {
        return new WithAuditingParameterInterceptor();
    }

    @Bean
    @ConditionalOnProperty(prefix = MybatisProperties.MYBATIS_PREFIX, name = "query-id.enabled", havingValue = "true")
    public Interceptor queryIdGenerationInterceptor() {
        log.info(">>> 쿼리ID 주석 자동 적용 활성화");
        return new QueryIdGenerationInterceptor(properties.queryIdPrefix());
    }
}
