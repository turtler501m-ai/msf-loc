package com.ktmmobile.msf.commons.common.config.client.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "external-service")
public record ExternalServiceProperties(
    Map<String, ServiceProperties> services
) {

    public record ServiceProperties(
        String url,
        Map<String, String> properties
    ) {
    }
}
