package com.ktmmobile.msf.commons.websecurity.security.auth.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.security.authorization")
public record SecurityAuthorizationProperties(
    List<String> permitAllUrls
) {

    public SecurityAuthorizationProperties {
        permitAllUrls = permitAllUrls == null ? List.of() : List.copyOf(permitAllUrls);
    }

    public String[] permitAllUrlPatterns() {
        return permitAllUrls.toArray(String[]::new);
    }
}
