package com.ktmmobile.msf.domains.form.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@MapperScan(
    basePackages = {
        "com.ktmmobile.msf.domains.form.form.common.mapper"
    },
    sqlSessionTemplateRef = "sqlSessionTemplate"
)
public class FormMapperConfig {
}
