package com.ktmmobile.msf.commons.websecurity.web.filter;

import java.util.Objects;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.body-cache")
public record BodyCacheProperties(
    CacheProperties request,
    CacheProperties response
) {

    public BodyCacheProperties {
        Objects.requireNonNull(request, "spring.body-cache.request is required");
        Objects.requireNonNull(response, "spring.body-cache.response is required");
    }

    public record CacheProperties(
        Integer limit
    ) {

        public CacheProperties {
            Objects.requireNonNull(limit, "spring.body-cache.*.limit is required");
            if (limit <= 0) {
                throw new IllegalArgumentException("spring.body-cache.*.limit must be greater than 0");
            }
        }
    }
}
