package com.ktmmobile.msf.domains.form.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

import com.ktmmobile.msf.commons.mybatis.config.SmartFormMyBatisConfig;

@Configuration(proxyBeanMethods = false)
@MapperScan(
    basePackages = {
        "com.ktmmobile.msf.domains.form.form.common.mapper"
    },
    sqlSessionTemplateRef = SmartFormMyBatisConfig.SQL_SESSION_TEMPLATE
)
public class FormMapperConfig {
}
