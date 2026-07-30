package com.ktmmobile.msf.commons.common.logging.http;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * HTTP 로그 공통 기본 룰 설정
 */
@ConfigurationProperties(prefix = "http-log")
public record HttpLogProperties(
    HttpLogRules defaultRules
) {

    /**
     * 공통 기본 룰 미설정 시 빈 룰 묶음 적용
     */
    public HttpLogProperties {
        defaultRules = defaultRules == null ? HttpLogRules.empty() : defaultRules;
    }
}
