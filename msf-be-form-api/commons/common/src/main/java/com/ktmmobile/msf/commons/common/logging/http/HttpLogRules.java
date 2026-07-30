package com.ktmmobile.msf.commons.common.logging.http;

/**
 * HTTP 로그 헤더/바디 공통 룰 묶음
 */
public record HttpLogRules(
    HttpLogMatchRule headerNames,
    HttpLogMatchRule bodyMaskedFields,
    HttpLogMatchRule bodyTruncatedFields
) {

    public HttpLogRules {
        headerNames = headerNames == null ? HttpLogMatchRule.empty() : headerNames;
        bodyMaskedFields = bodyMaskedFields == null ? HttpLogMatchRule.empty() : bodyMaskedFields;
        bodyTruncatedFields = bodyTruncatedFields == null ? HttpLogMatchRule.empty() : bodyTruncatedFields;
    }

    /**
     * 빈 HTTP 로그 룰 묶음 생성
     */
    public static HttpLogRules empty() {
        return new HttpLogRules(null, null, null);
    }

    /**
     * 기본 HTTP 로그 룰 묶음에 override 룰 묶음 병합
     */
    public HttpLogRules merge(HttpLogRules override) {
        if (override == null) {
            return this;
        }
        return new HttpLogRules(
            headerNames.merge(override.headerNames),
            bodyMaskedFields.merge(override.bodyMaskedFields),
            bodyTruncatedFields.merge(override.bodyTruncatedFields)
        );
    }
}
