package com.ktmmobile.msf.commons.common.datasource.common.property;

import java.util.Map;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.datasource")
public record DataSourcesProperties(
    Map<String, DataSourceGroupProperties> groups,
    HikariConfig hikari
) {

}
