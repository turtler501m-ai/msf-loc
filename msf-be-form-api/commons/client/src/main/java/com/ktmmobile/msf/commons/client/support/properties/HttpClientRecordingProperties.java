package com.ktmmobile.msf.commons.client.support.properties;

import java.util.Objects;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.ktmmobile.msf.commons.common.logging.http.HttpLogMatchRule;
import com.ktmmobile.msf.commons.common.logging.http.HttpLogRules;

/**
 * HTTP client 요청/응답 기록 정책
 */
@ConfigurationProperties(prefix = "http-client.recording")
public record HttpClientRecordingProperties(
    Boolean enabled,
    Integer responseBodyTruncatedSize,
    HttpLogMatchRule headerNames,
    HttpLogMatchRule bodyMaskedFields,
    HttpLogMatchRule bodyTruncatedFields
) {

    /**
     * HTTP client 기록 정책 필수값 검증
     */
    public HttpClientRecordingProperties {
        Objects.requireNonNull(enabled, "http-client.recording.enabled is required");
        if (responseBodyTruncatedSize == null || responseBodyTruncatedSize < 0) {
            throw new IllegalArgumentException("http-client.recording.response-body-truncated-size must be greater than or equal to 0");
        }
    }

    /**
     * 공통 HTTP 로그 룰 묶음 변환
     */
    public HttpLogRules httpLogRules() {
        return new HttpLogRules(headerNames, bodyMaskedFields, bodyTruncatedFields);
    }
}
