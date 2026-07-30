package com.ktmmobile.msf.commons.client.application.service;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.client.domain.dto.HttpClientLog;
import com.ktmmobile.msf.commons.client.support.properties.HttpClientRecordingProperties;
import com.ktmmobile.msf.commons.common.logging.http.HttpExchangeLogSanitizer;
import com.ktmmobile.msf.commons.common.logging.http.HttpLogMatchRule;
import com.ktmmobile.msf.commons.common.logging.http.HttpLogProperties;
import com.ktmmobile.msf.commons.common.logging.http.HttpLogRules;

/**
 * HTTP client 요청/응답 기록 데이터 생성기
 */
public class HttpClientLogFactory {

    private static final String TRACE_ID_MDC_KEY = "traceId";
    private static final String URI_TEMPLATE_ATTRIBUTE = "org.springframework.web.client.RestClient.uriTemplate";
    private static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";
    private static final Map<String, List<String>> FORM_URLENCODED_HEADERS = Map.of(
        CONTENT_TYPE_HEADER_NAME,
        List.of("application/x-www-form-urlencoded")
    );
    private static final String RESPONSE_BODY_OMITTED = "<response body omitted>";

    private final ObjectMapper objectMapper;
    private final HttpExchangeLogSanitizer sanitizedBodySanitizer;
    private final HttpExchangeLogSanitizer retainedBodySanitizer;
    private final HttpClientRecordingProperties recordingProperties;

    /**
     * 공통 HTTP 로그 룰과 HTTP client 기록 정책 기반 생성
     */
    public HttpClientLogFactory(
        ObjectMapper objectMapper,
        HttpLogProperties httpLogProperties,
        HttpClientRecordingProperties recordingProperties
    ) {
        this.objectMapper = objectMapper;
        this.recordingProperties = recordingProperties;
        HttpLogRules httpLogRules = httpLogProperties.defaultRules().merge(recordingProperties.httpLogRules());
        this.sanitizedBodySanitizer = new HttpExchangeLogSanitizer(
            objectMapper,
            httpLogRules.headerNames(),
            httpLogRules.bodyMaskedFields(),
            httpLogRules.bodyTruncatedFields()
        );
        this.retainedBodySanitizer = new HttpExchangeLogSanitizer(
            objectMapper,
            httpLogRules.headerNames(),
            httpLogRules.bodyMaskedFields(),
            HttpLogMatchRule.empty()
        );
    }

    /**
     * HTTP 응답을 받은 호출 기록 생성
     */
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
        Map<String, List<String>> responseHeaders = copyRawHeaders(response.getHeaders());
        String sanitizedResponseBody = responseBodyToText(responseHeaders, responseBody);
        String retainedResponseBody = retainedResponseBodyToText(responseHeaders, responseBody);
        return new HttpClientLog(
            traceId(),
            groupName,
            request.getMethod().name(),
            request.getURI().toString(),
            pathToText(request),
            queryToText(request.getURI()),
            new HttpClientLog.Request(
                copyHeaders(request.getHeaders()),
                sanitizedBodySanitizer.toText(copyRawHeaders(request.getHeaders()), requestBody),
                retainedBodySanitizer.toText(copyRawHeaders(request.getHeaders()), requestBody)
            ),
            new HttpClientLog.Response(
                status,
                copyHeaders(response.getHeaders()),
                sanitizedResponseBody,
                retainedResponseBody
            ),
            durationMs,
            success,
            success ? null : errorMessage(sanitizedResponseBody),
            requestedAt
        );
    }

    /**
     * HTTP 응답을 받지 못한 예외 호출 기록 생성
     */
    public HttpClientLog createErrorLog(
        String groupName,
        HttpRequest request,
        byte[] requestBody,
        long durationMs,
        Instant requestedAt,
        Exception exception
    ) {
        return new HttpClientLog(
            traceId(),
            groupName,
            request.getMethod().name(),
            request.getURI().toString(),
            pathToText(request),
            queryToText(request.getURI()),
            new HttpClientLog.Request(
                copyHeaders(request.getHeaders()),
                sanitizedBodySanitizer.toText(copyRawHeaders(request.getHeaders()), requestBody),
                retainedBodySanitizer.toText(copyRawHeaders(request.getHeaders()), requestBody)
            ),
            null,
            durationMs,
            false,
            exception.getMessage(),
            requestedAt
        );
    }

    private String traceId() {
        return MDC.get(TRACE_ID_MDC_KEY);
    }

    private String pathToText(HttpRequest request) {
        Object uriTemplate = request.getAttributes().get(URI_TEMPLATE_ATTRIBUTE);
        if (!(uriTemplate instanceof String template) || template.isBlank()) {
            return "{}";
        }

        try {
            return objectMapper.writeValueAsString(extractPathVariables(template, request.getURI()));
        } catch (JacksonException _) {
            return "{}";
        }
    }

    private Map<String, String> extractPathVariables(String uriTemplate, URI uri) {
        List<String> templateSegments = pathSegments(templatePath(uriTemplate));
        List<String> actualSegments = pathSegments(uri.getRawPath());
        Map<String, String> pathVariables = new LinkedHashMap<>();

        // URI template의 path variable 이름과 실제 요청 path segment 값 매핑
        int segmentCount = Math.min(templateSegments.size(), actualSegments.size());
        for (int index = 0; index < segmentCount; index++) {
            String variableName = pathVariableName(templateSegments.get(index));
            if (variableName != null) {
                pathVariables.put(variableName, decodePathSegment(actualSegments.get(index)));
            }
        }
        return pathVariables;
    }

    private String templatePath(String uriTemplate) {
        int queryIndex = uriTemplate.indexOf('?');
        String path = queryIndex >= 0 ? uriTemplate.substring(0, queryIndex) : uriTemplate;
        int schemeIndex = path.indexOf("://");
        if (schemeIndex < 0) {
            return path;
        }

        int pathStartIndex = path.indexOf('/', schemeIndex + 3);
        return pathStartIndex < 0 ? "" : path.substring(pathStartIndex);
    }

    private List<String> pathSegments(String path) {
        if (path == null || path.isBlank()) {
            return List.of();
        }
        return java.util.Arrays.stream(path.split("/"))
            .filter(segment -> !segment.isBlank())
            .toList();
    }

    private String pathVariableName(String templateSegment) {
        if (!templateSegment.startsWith("{") || !templateSegment.endsWith("}")) {
            return null;
        }

        String variableExpression = templateSegment.substring(1, templateSegment.length() - 1);
        int regexSeparatorIndex = variableExpression.indexOf(':');
        String variableName = regexSeparatorIndex >= 0 ? variableExpression.substring(0, regexSeparatorIndex) : variableExpression;
        return variableName.isBlank() ? null : variableName;
    }

    private String decodePathSegment(String segment) {
        return URLDecoder.decode(segment, StandardCharsets.UTF_8);
    }

    private String queryToText(URI uri) {
        String rawQuery = uri.getRawQuery();
        if (rawQuery == null || rawQuery.isBlank()) {
            return "";
        }
        return sanitizedBodySanitizer.toText(FORM_URLENCODED_HEADERS, rawQuery.getBytes(StandardCharsets.UTF_8));
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

    private String errorMessage(String responseBodyText) {
        return responseBodyText == null || responseBodyText.isBlank() ? "" : null;
    }

    private String responseBodyToText(Map<String, List<String>> headers, byte[] responseBody) {
        if (responseBody == null || responseBody.length == 0) {
            return "";
        }

        String contentType = firstHeaderValue(headers, CONTENT_TYPE_HEADER_NAME);
        String normalizedContentType = normalizeContentType(contentType);
        if (isJson(normalizedContentType)) {
            // JSON 응답은 공통 sanitizer의 필드 마스킹/축약 룰 적용
            return sanitizedBodySanitizer.toText(headers, responseBody);
        }

        if (isText(normalizedContentType)) {
            // text 응답은 설정된 길이만큼 축약 저장
            return textResponseBody(responseBody, contentType);
        }

        return RESPONSE_BODY_OMITTED;
    }

    private String textResponseBody(byte[] responseBody, String contentType) {
        String bodyText = new String(responseBody, resolveCharset(contentType));
        int truncatedSize = recordingProperties.responseBodyTruncatedSize();
        if (truncatedSize == 0 || bodyText.length() <= truncatedSize) {
            return bodyText;
        }
        return bodyText.substring(0, truncatedSize) + "...<truncated: response-body-truncated-size=" + truncatedSize + ">";
    }

    private String retainedResponseBodyToText(Map<String, List<String>> headers, byte[] responseBody) {
        if (responseBody == null || responseBody.length == 0) {
            return "";
        }

        String contentType = firstHeaderValue(headers, CONTENT_TYPE_HEADER_NAME);
        String normalizedContentType = normalizeContentType(contentType);
        if (isJson(normalizedContentType) || isText(normalizedContentType)) {
            return retainedBodySanitizer.toText(headers, responseBody);
        }

        return RESPONSE_BODY_OMITTED;
    }

    private Map<String, List<String>> copyHeaders(HttpHeaders headers) {
        return sanitizedBodySanitizer.copyHeaders(copyRawHeaders(headers));
    }

    private Map<String, List<String>> copyRawHeaders(HttpHeaders headers) {
        Map<String, List<String>> copied = new LinkedHashMap<>();
        headers.forEach((name, values) -> copied.put(name, List.copyOf(values)));
        return copied;
    }

    private String firstHeaderValue(Map<String, List<String>> headers, String headerName) {
        return headers.entrySet()
            .stream()
            .filter(entry -> entry.getKey().equalsIgnoreCase(headerName))
            .findFirst()
            .map(Map.Entry::getValue)
            .filter(values -> !values.isEmpty())
            .map(List::getFirst)
            .orElse("");
    }

    private String normalizeContentType(String contentType) {
        if (contentType == null || contentType.isBlank()) {
            return "";
        }

        int separatorIndex = contentType.indexOf(';');
        String mediaType = separatorIndex >= 0 ? contentType.substring(0, separatorIndex) : contentType;
        return mediaType.trim().toLowerCase(java.util.Locale.ROOT);
    }

    private boolean isJson(String contentType) {
        return contentType.equals(MediaType.APPLICATION_JSON_VALUE)
            || contentType.endsWith("+json");
    }

    private boolean isText(String contentType) {
        return contentType.startsWith("text/");
    }

    private Charset resolveCharset(String contentType) {
        if (contentType == null || contentType.isBlank()) {
            return StandardCharsets.UTF_8;
        }

        String charsetPrefix = "charset=";
        int charsetPrefixLength = charsetPrefix.length();
        for (String part: contentType.split(";")) {
            String trimmedPart = part.trim();
            if (trimmedPart.regionMatches(true, 0, charsetPrefix, 0, charsetPrefixLength)) {
                try {
                    return Charset.forName(trimmedPart.substring(charsetPrefixLength).trim());
                } catch (IllegalArgumentException _) {
                    return StandardCharsets.UTF_8;
                }
            }
        }
        return StandardCharsets.UTF_8;
    }
}
