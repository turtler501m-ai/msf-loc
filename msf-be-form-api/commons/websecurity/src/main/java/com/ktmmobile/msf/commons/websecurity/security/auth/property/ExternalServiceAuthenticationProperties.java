package com.ktmmobile.msf.commons.websecurity.security.auth.property;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

/**
 * 외부 서비스 호출 수신 인증 설정
 */
@ConfigurationProperties(prefix = "external-service-auth")
public record ExternalServiceAuthenticationProperties(
    String apiKeyHeaderName,
    Map<String, ExternalServiceAuthenticationServiceProperties> services
) {

    public ExternalServiceAuthenticationProperties {
        Assert.hasText(apiKeyHeaderName, "external-service-auth.api-key-header-name is required");
        services = services == null ? Map.of() : Map.copyOf(services);
    }

    public boolean requiresAuthentication() {
        return !services.isEmpty();
    }
}
