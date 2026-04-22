package com.ktmmobile.msf.commons.client.support.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "internal-service")
public record InternalServiceProperties(
    Map<String, ServiceProperties> services
) {

    public InternalServiceProperties {
        services = services == null ? Map.of() : Map.copyOf(services);
    }

    public ServiceProperties service(String serviceName) {
        return services.get(serviceName);
    }
}
