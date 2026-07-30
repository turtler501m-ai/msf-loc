package com.ktmmobile.msf.domains.accesstrace.support;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.common.logging.http.HttpExchangeLogSanitizer;
import com.ktmmobile.msf.commons.common.logging.http.HttpLogMatchRule;
import com.ktmmobile.msf.commons.common.logging.http.HttpLogProperties;
import com.ktmmobile.msf.commons.common.logging.http.HttpLogRules;
import com.ktmmobile.msf.commons.websecurity.web.accesstrace.AccessTraceParameterSanitizer;

/**
 * YAML 설정 기반 API 요청 이력 PARAMETER 마스킹 및 축약 처리기
 */
@Component
public class DefaultAccessTraceParameterSanitizer implements AccessTraceParameterSanitizer {

    private static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";
    private static final String FORM_URL_ENCODED_CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final String TRUNCATED_SUFFIX_FORMAT = "...<truncated: access-trace.parameter.max-length=%d>";

    private final AccessTraceProperties.ParameterProperties parameterProperties;
    private final HttpExchangeLogSanitizer logSanitizer;

    /**
     * 공통 HTTP 로그 sanitizer를 API 요청 이력 설정으로 초기화
     */
    public DefaultAccessTraceParameterSanitizer(
        ObjectMapper objectMapper,
        HttpLogProperties httpLogProperties,
        AccessTraceProperties accessTraceProperties
    ) {
        this.parameterProperties = accessTraceProperties.parameter();
        HttpLogRules httpLogRules = httpLogProperties.defaultRules().merge(parameterProperties.httpLogRules());
        this.logSanitizer = new HttpExchangeLogSanitizer(
            objectMapper,
            HttpLogMatchRule.empty(),
            httpLogRules.bodyMaskedFields(),
            httpLogRules.bodyTruncatedFields()
        );
    }

    /**
     * 요청 본문 마스킹 및 축약
     */
    @Override
    public String sanitizeBody(String contentType, Charset charset, String bodyText) {
        if (!StringUtils.hasText(bodyText)) {
            return "";
        }
        return logSanitizer.toText(
            headers(contentType),
            bodyText.getBytes(charset == null ? StandardCharsets.UTF_8 : charset)
        );
    }

    /**
     * QueryString을 form-urlencoded 규칙으로 마스킹 및 축약
     */
    @Override
    public String sanitizeQueryString(String queryString, Charset charset) {
        if (!StringUtils.hasText(queryString)) {
            return "";
        }
        return logSanitizer.toText(
            headers(FORM_URL_ENCODED_CONTENT_TYPE),
            queryString.getBytes(charset == null ? StandardCharsets.UTF_8 : charset)
        );
    }

    /**
     * PARAMETER 컬럼 최대 길이 이내로 축약
     */
    @Override
    public String limitParameter(String parameter) {
        if (parameter == null) {
            return "";
        }
        int maxLength = parameterProperties.maxLength();
        if (parameter.length() <= maxLength) {
            return parameter;
        }

        String suffix = TRUNCATED_SUFFIX_FORMAT.formatted(maxLength);
        if (suffix.length() >= maxLength) {
            return suffix.substring(0, maxLength);
        }
        int contentLength = maxLength - suffix.length();
        return parameter.substring(0, contentLength) + suffix;
    }

    /**
     * Content-Type 헤더 맵 생성
     */
    private Map<String, List<String>> headers(String contentType) {
        if (!StringUtils.hasText(contentType)) {
            return Map.of();
        }
        return Map.of(CONTENT_TYPE_HEADER_NAME, List.of(contentType));
    }

}
