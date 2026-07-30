package com.ktmmobile.msf.domains.externalclient.common.property;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 내부 연동 서비스 설정
 */
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
