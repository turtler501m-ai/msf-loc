package com.ktmmobile.msf.commons.websecurity.web.filter;

import java.util.List;
import java.util.Objects;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.ktmmobile.msf.commons.common.logging.http.HttpLogMatchRule;
import com.ktmmobile.msf.commons.common.logging.http.HttpLogRules;

@ConfigurationProperties(prefix = "spring.request-log")
public record RequestLogProperties(
    List<String> excludeUrlPatterns,
    EnabledProperties enabled,
    Integer maxBodyLength,
    List<String> responseBodyContentTypes,
    HttpLogMatchRule headerNames,
    HttpLogMatchRule bodyMaskedFields,
    HttpLogMatchRule bodyTruncatedFields
) {

    public RequestLogProperties {
        excludeUrlPatterns = List.copyOf(Objects.requireNonNull(excludeUrlPatterns, "spring.request-log.exclude-url-patterns is required"));
        Objects.requireNonNull(enabled, "spring.request-log.enabled is required");
        Objects.requireNonNull(maxBodyLength, "spring.request-log.max-body-length is required");
        if (maxBodyLength <= 0) {
            throw new IllegalArgumentException("spring.request-log.max-body-length must be greater than 0");
        }
        responseBodyContentTypes = List.copyOf(Objects.requireNonNull(
            responseBodyContentTypes,
            "spring.request-log.response-body-content-types is required"
        ));
    }

    public HttpLogRules httpLogRules() {
        return new HttpLogRules(headerNames, bodyMaskedFields, bodyTruncatedFields);
    }

    public record EnabledProperties(
        Boolean requestHeaders,
        Boolean responseHeaders,
        Boolean requestBody,
        Boolean responseBody
    ) {

        public EnabledProperties {
            Objects.requireNonNull(requestHeaders, "spring.request-log.enabled.request-headers is required");
            Objects.requireNonNull(responseHeaders, "spring.request-log.enabled.response-headers is required");
            Objects.requireNonNull(requestBody, "spring.request-log.enabled.request-body is required");
            Objects.requireNonNull(responseBody, "spring.request-log.enabled.response-body is required");
        }
    }
}
