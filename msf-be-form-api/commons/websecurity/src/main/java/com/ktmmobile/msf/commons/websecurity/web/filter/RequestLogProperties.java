package com.ktmmobile.msf.commons.websecurity.web.filter;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.request-log")
public record RequestLogProperties(
    List<String> excludeUrlPatterns
) {

    public RequestLogProperties {
        excludeUrlPatterns = excludeUrlPatterns == null ? List.of() : List.copyOf(excludeUrlPatterns);
    }
}
