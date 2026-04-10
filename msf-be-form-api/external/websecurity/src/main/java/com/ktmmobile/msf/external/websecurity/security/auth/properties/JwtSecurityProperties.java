package com.ktmmobile.msf.external.websecurity.security.auth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.security.jwt")
public record JwtSecurityProperties(
    String secretKey,
    String issuer
) {
}
