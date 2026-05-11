package com.ktmmobile.msf.commons.websecurity.security.auth.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.security.authorization")
public record SecurityAuthorizationProperties(
    List<String> permitAllUrls,
    List<String> tokenIgnoreUrls
) {

    public SecurityAuthorizationProperties {
        permitAllUrls = permitAllUrls == null ? List.of() : List.copyOf(permitAllUrls);
        tokenIgnoreUrls = tokenIgnoreUrls == null ? List.of("/api/auth/**") : List.copyOf(tokenIgnoreUrls);
    }

    public String[] permitAllUrlPatterns() {
        return permitAllUrls.toArray(String[]::new);
    }

    public String[] tokenIgnoreUrlPatterns() {
        return tokenIgnoreUrls.toArray(String[]::new);
    }
}
