package com.ktmmobile.msf.commons.client.support.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "http-client.logging")
public record HttpClientLoggingProperties(
    boolean enabled,
    MatchRuleProperties headerNames,
    MatchRuleProperties bodyMaskedFields,
    MatchRuleProperties bodyTruncatedFields
) {

    public HttpClientLoggingProperties {
        headerNames = headerNames == null ? MatchRuleProperties.empty() : headerNames;
        bodyMaskedFields = bodyMaskedFields == null ? MatchRuleProperties.empty() : bodyMaskedFields;
        bodyTruncatedFields = bodyTruncatedFields == null ? MatchRuleProperties.empty() : bodyTruncatedFields;
    }

    public record MatchRuleProperties(
        List<String> include,
        List<String> includePrepend,
        List<String> exclude,
        List<String> excludePrepend,
        int defaultTruncatedSize
    ) {

        public MatchRuleProperties {
            include = include == null ? List.of() : List.copyOf(include);
            includePrepend = includePrepend == null ? List.of() : List.copyOf(includePrepend);
            exclude = exclude == null ? List.of() : List.copyOf(exclude);
            excludePrepend = excludePrepend == null ? List.of() : List.copyOf(excludePrepend);
            defaultTruncatedSize = Math.max(defaultTruncatedSize, 0);
        }

        public static MatchRuleProperties empty() {
            return new MatchRuleProperties(List.of(), List.of(), List.of(), List.of(), 0);
        }

        public List<String> mergedInclude() {
            List<String> merged = new ArrayList<>(includePrepend);
            merged.addAll(include);
            return List.copyOf(merged);
        }

        public List<String> mergedExclude() {
            List<String> merged = new ArrayList<>(excludePrepend);
            merged.addAll(exclude);
            return List.copyOf(merged);
        }
    }
}
