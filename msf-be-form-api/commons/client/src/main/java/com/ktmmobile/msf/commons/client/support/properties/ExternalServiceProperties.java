package com.ktmmobile.msf.commons.client.support.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "external-service")
public record ExternalServiceProperties(
    Map<String, ServiceProperties> services
) {

    public ExternalServiceProperties {
        services = services == null ? Map.of() : Map.copyOf(services);
    }

    public ServiceProperties service(String serviceName) {
        return services.get(serviceName);
    }
}
