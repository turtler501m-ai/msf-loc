package com.ktmmobile.msf.domains.externalclient.common.property;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 외부 연동 서비스 설정
 */
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
