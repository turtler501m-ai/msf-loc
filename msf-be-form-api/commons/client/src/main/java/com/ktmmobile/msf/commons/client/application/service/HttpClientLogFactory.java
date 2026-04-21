package com.ktmmobile.msf.commons.client.application.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import com.ktmmobile.msf.commons.client.domain.dto.HttpClientLog;
import com.ktmmobile.msf.commons.client.support.properties.HttpClientLoggingProperties;
import com.ktmmobile.msf.commons.client.support.properties.HttpClientLoggingProperties.MatchRuleProperties;

public class HttpClientLogFactory {

    private static final Pattern SIZE_RULE_PATTERN = Pattern.compile("^(.+)\\[(\\d+)(?::(\\d+))?]$");

    private final ObjectMapper objectMapper;
    private final List<HeaderLogRule> loggingHeaderRules;
    private final Set<String> headerNameExcludes;
    private final Set<String> bodyMaskedIncludes;
    private final Set<String> bodyMaskedExcludes;
    private final List<BodyTruncateRule> bodyTruncatedIncludes;
    private final Set<String> bodyTruncatedExcludes;

    public HttpClientLogFactory(
        ObjectMapper objectMapper,
        HttpClientLoggingProperties loggingProperties
    ) {
        this.objectMapper = objectMapper;
        this.loggingHeaderRules = toHeaderLogRules(loggingProperties.headerNames());
        this.headerNameExcludes = toNormalizedMatchSet(loggingProperties.headerNames().mergedExclude());
        this.bodyMaskedIncludes = toNormalizedMatchSet(loggingProperties.bodyMaskedFields().mergedInclude());
        this.bodyMaskedExcludes = toNormalizedMatchSet(loggingProperties.bodyMaskedFields().mergedExclude());
        this.bodyTruncatedIncludes = toBodyTruncateRules(loggingProperties.bodyTruncatedFields().mergedInclude(),
            loggingProperties.bodyTruncatedSize());
        this.bodyTruncatedExcludes = toNormalizedMatchSet(loggingProperties.bodyTruncatedFields().mergedExclude());
    }

    public HttpClientLog createSuccessLog(
        String groupName,
        HttpRequest request,
        byte[] requestBody,
        ClientHttpResponse response,
        byte[] responseBody,
        long durationMs,
        Instant requestedAt
    ) {
        Integer status = getStatus(response);
        boolean success = isSuccessfulStatus(status);
        return new HttpClientLog(
            groupName,
            request.getMethod().name(),
            request.getURI().toString(),
            new HttpClientLog.Request(copyHeaders(request.getHeaders()), toText(request.getHeaders(), requestBody)),
            new HttpClientLog.Response(
                status,
                copyHeaders(response.getHeaders()),
                toText(response.getHeaders(), responseBody)
            ),
            durationMs,
            success,
            success ? null : buildStatusErrorMessage(status),
            requestedAt
        );
    }

    public HttpClientLog createErrorLog(
        String groupName,
        HttpRequest request,
        byte[] requestBody,
        long durationMs,
        Instant requestedAt,
        Exception exception
    ) {
        return new HttpClientLog(
            groupName,
            request.getMethod().name(),
            request.getURI().toString(),
            new HttpClientLog.Request(copyHeaders(request.getHeaders()), toText(request.getHeaders(), requestBody)),
            null,
            durationMs,
            false,
            exception.getMessage(),
            requestedAt
        );
    }

    private Integer getStatus(ClientHttpResponse response) {
        try {
            return response.getStatusCode().value();
        } catch (IOException _) {
            return null;
        }
    }

    private boolean isSuccessfulStatus(Integer status) {
        return status != null && status >= 200 && status < 300;
    }

    private String buildStatusErrorMessage(Integer status) {
        return status == null ? "HTTP status unavailable" : "HTTP status=" + status;
    }

    private Map<String, List<String>> copyHeaders(HttpHeaders headers) {
        Map<String, List<String>> copied = new LinkedHashMap<>();
        headers.forEach((name, values) -> {
            HeaderLogRule headerLogRule = findHeaderLogRule(name);
            if (headerLogRule != null) {
                copied.put(name, applyLogRule(values, headerLogRule));
            }
        });
        return copied;
    }

    private HeaderLogRule findHeaderLogRule(String headerName) {
        String normalizedHeaderName = headerName.toLowerCase();
        if (matchesAny(normalizedHeaderName, headerNameExcludes)) {
            return null;
        }

        return loggingHeaderRules.stream()
            .filter(rule -> normalizedHeaderName.contains(rule.normalizedMatchToken()))
            .findFirst()
            .orElse(null);
    }

    private List<String> applyLogRule(List<String> values, HeaderLogRule headerLogRule) {
        if (headerLogRule.raw()) {
            return List.copyOf(values);
        }

        return values.stream()
            .map(value -> maskHeaderValue(value, headerLogRule))
            .toList();
    }

    private String maskHeaderValue(String value, HeaderLogRule headerLogRule) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        int valueLength = value.length();
        int prefixLength = Math.min(headerLogRule.prefixLength(), valueLength);
        int suffixStart = Math.max(prefixLength, valueLength - headerLogRule.suffixLength());

        String prefix = value.substring(0, prefixLength);
        String suffix = suffixStart >= valueLength ? "" : value.substring(suffixStart);
        boolean hasMaskedMiddle = suffixStart > prefixLength;

        return prefix
            + (hasMaskedMiddle ? "..." : "")
            + suffix
            + " (length=" + valueLength + ")";
    }

    private String toText(HttpHeaders headers, byte[] body) {
        if (body == null || body.length == 0) {
            return "";
        }

        // content-type에 charset이 있으면 우선 사용하고, 없으면 UTF-8을 기본값으로 본다.
        Charset charset = StandardCharsets.UTF_8;
        MediaType contentType = headers.getContentType();
        if (contentType != null && contentType.getCharset() != null) {
            charset = contentType.getCharset();
        }

        String bodyText = new String(body, charset);
        if (contentType != null && contentType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
            return maskJsonBody(bodyText);
        }

        return bodyText;
    }

    private String maskJsonBody(String bodyText) {
        if ((bodyMaskedIncludes.isEmpty() && bodyTruncatedIncludes.isEmpty()) || bodyText.isBlank()) {
            return bodyText;
        }

        try {
            JsonNode rootNode = objectMapper.readTree(bodyText);
            maskJsonNode(rootNode);
            return objectMapper.writeValueAsString(rootNode);
        } catch (JsonProcessingException _) {
            return bodyText;
        }
    }

    private void maskJsonNode(JsonNode node) {
        if (node == null) {
            return;
        }

        if (node.isObject()) {
            maskObjectNode((ObjectNode) node);
            return;
        }

        if (node.isArray()) {
            maskArrayNode((ArrayNode) node);
        }
    }

    private void maskObjectNode(ObjectNode objectNode) {
        objectNode.fieldNames().forEachRemaining(fieldName -> {
            JsonNode childNode = objectNode.get(fieldName);
            if (shouldMaskBodyField(fieldName) && childNode != null && !childNode.isNull()) {
                objectNode.put(fieldName, maskBodyValue(childNode.asText()));
                return;
            }

            BodyTruncateRule bodyTruncateRule = findBodyTruncateRule(fieldName);
            if (bodyTruncateRule != null && childNode != null && childNode.isTextual()) {
                objectNode.put(fieldName, truncateBodyValue(childNode.asText(), bodyTruncateRule));
                return;
            }

            maskJsonNode(childNode);
        });
    }

    private void maskArrayNode(ArrayNode arrayNode) {
        arrayNode.forEach(this::maskJsonNode);
    }

    private boolean shouldMaskBodyField(String fieldName) {
        String normalizedFieldName = fieldName.toLowerCase();
        return matchesAny(normalizedFieldName, bodyMaskedIncludes)
            && !matchesAny(normalizedFieldName, bodyMaskedExcludes);
    }

    private BodyTruncateRule findBodyTruncateRule(String fieldName) {
        String normalizedFieldName = fieldName.toLowerCase();
        if (matchesAny(normalizedFieldName, bodyTruncatedExcludes)) {
            return null;
        }

        return bodyTruncatedIncludes.stream()
            .filter(rule -> normalizedFieldName.contains(rule.normalizedMatchToken()))
            .findFirst()
            .orElse(null);
    }

    private List<HeaderLogRule> toHeaderLogRules(MatchRuleProperties headerRuleProperties) {
        List<HeaderLogRule> headerLogRules = new ArrayList<>();
        headerRuleProperties.mergedInclude().forEach(headerExpression -> headerLogRules.add(HeaderLogRule.parse(headerExpression)));
        return headerLogRules;
    }

    private List<BodyTruncateRule> toBodyTruncateRules(List<String> fieldExpressions, int defaultSize) {
        List<BodyTruncateRule> bodyTruncateRules = new ArrayList<>();
        fieldExpressions.forEach(fieldExpression -> bodyTruncateRules.add(BodyTruncateRule.parse(fieldExpression, defaultSize)));
        return bodyTruncateRules;
    }

    private Set<String> toNormalizedMatchSet(List<String> matchExpressions) {
        return new HashSet<>(matchExpressions.stream()
            .map(this::normalizeMatchExpression)
            .toList());
    }

    private String normalizeMatchExpression(String expression) {
        if (expression == null || expression.isBlank()) {
            return "";
        }

        String trimmedExpression = expression.trim();
        Matcher matcher = SIZE_RULE_PATTERN.matcher(trimmedExpression);
        if (matcher.matches()) {
            return matcher.group(1).trim().toLowerCase();
        }

        return trimmedExpression.toLowerCase();
    }

    private boolean matchesAny(String target, Set<String> matchTokens) {
        return matchTokens.stream()
            .filter(token -> !token.isBlank())
            .anyMatch(target::contains);
    }

    private String maskBodyValue(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        return "****** (length=" + value.length() + ")";
    }

    private String truncateBodyValue(String value, BodyTruncateRule bodyTruncateRule) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        int prefixLength = bodyTruncateRule.prefixLength();
        int suffixLength = bodyTruncateRule.suffixLength();
        if (prefixLength <= 0 || suffixLength <= 0 || value.length() <= prefixLength + suffixLength) {
            return value;
        }

        String prefix = value.substring(0, Math.min(prefixLength, value.length()));
        String suffix = value.substring(Math.max(prefix.length(), value.length() - suffixLength));
        return prefix + "..." + suffix + " (length=" + value.length() + ")";
    }

    private record HeaderLogRule(
        String normalizedMatchToken,
        boolean raw,
        int prefixLength,
        int suffixLength
    ) {

        private static HeaderLogRule parse(String expression) {
            if (expression == null || expression.isBlank()) {
                throw new IllegalArgumentException("http-client.logging.header-names.include 값은 '헤더명', '헤더명[글자수]' 또는 '헤더명[앞글자수:뒤글자수]' 형식이어야 합니다.");
            }

            String trimmedExpression = expression.trim();
            Matcher matcher = SIZE_RULE_PATTERN.matcher(trimmedExpression);
            if (matcher.matches()) {
                try {
                    String headerName = matcher.group(1).trim();
                    int prefixLength = Integer.parseInt(matcher.group(2));
                    int suffixLength = matcher.group(3) == null ? prefixLength : Integer.parseInt(matcher.group(3));
                    return new HeaderLogRule(headerName.toLowerCase(), false, prefixLength, suffixLength);
                } catch (NumberFormatException exception) {
                    throw new IllegalArgumentException(
                        "http-client.logging.header-names.include 값은 '헤더명[숫자]' 또는 '헤더명[숫자:숫자]' 형식이어야 합니다. value=" + expression,
                        exception
                    );
                }
            }

            if (trimmedExpression.contains("[") || trimmedExpression.contains("]")) {
                throw new IllegalArgumentException(
                    "http-client.logging.header-names.include 값은 '헤더명', '헤더명[글자수]' 또는 '헤더명[앞글자수:뒤글자수]' 형식이어야 합니다. value=" + expression
                );
            }

            return new HeaderLogRule(trimmedExpression.toLowerCase(), true, 0, 0);
        }
    }

    private record BodyTruncateRule(
        String normalizedMatchToken,
        int prefixLength,
        int suffixLength
    ) {

        private static BodyTruncateRule parse(String expression, int defaultSize) {
            if (expression == null || expression.isBlank()) {
                throw new IllegalArgumentException(
                    "http-client.logging.body-truncated-fields.include 값은 '필드명', '필드명[글자수]' 또는 '필드명[앞글자수:뒤글자수]' 형식이어야 합니다."
                );
            }

            String trimmedExpression = expression.trim();
            Matcher matcher = SIZE_RULE_PATTERN.matcher(trimmedExpression);
            if (matcher.matches()) {
                try {
                    String fieldName = matcher.group(1).trim();
                    int prefixLength = Integer.parseInt(matcher.group(2));
                    int suffixLength = matcher.group(3) == null ? prefixLength : Integer.parseInt(matcher.group(3));
                    return new BodyTruncateRule(fieldName.toLowerCase(), prefixLength, suffixLength);
                } catch (NumberFormatException exception) {
                    throw new IllegalArgumentException(
                        "http-client.logging.body-truncated-fields.include 값은 '필드명[숫자]' 또는 '필드명[숫자:숫자]' 형식이어야 합니다. value=" + expression,
                        exception
                    );
                }
            }

            if (trimmedExpression.contains("[") || trimmedExpression.contains("]")) {
                throw new IllegalArgumentException(
                    "http-client.logging.body-truncated-fields.include 값은 '필드명', '필드명[글자수]' 또는 '필드명[앞글자수:뒤글자수]' 형식이어야 합니다. value=" + expression
                );
            }

            return new BodyTruncateRule(trimmedExpression.toLowerCase(), defaultSize, defaultSize);
        }
    }
}
