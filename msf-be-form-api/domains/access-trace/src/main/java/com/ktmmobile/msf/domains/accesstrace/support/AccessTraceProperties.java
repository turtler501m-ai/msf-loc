package com.ktmmobile.msf.domains.accesstrace.support;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;
import com.ktmmobile.msf.commons.common.logging.http.HttpLogMatchRule;
import com.ktmmobile.msf.commons.common.logging.http.HttpLogRules;

/**
 * API 요청 이력의 처리 모듈 구분 코드를 YAML 설정에서 읽어오는 속성
 *
 * @param enabled API 요청 이력 사용 여부
 * @param apiPathPrefix API 요청 이력 기록 대상 경로 prefix
 * @param excludeUrlPatterns API 요청 이력 제외 URL 패턴 목록
 * @param parameter PARAMETER 컬럼 마스킹 및 축약 설정
 * @param moduleCodes 처리 모듈 구분 코드별 URL 패턴 목록
 */
@ConfigurationProperties(prefix = "access-trace")
public record AccessTraceProperties(
    Boolean enabled,
    String apiPathPrefix,
    List<String> excludeUrlPatterns,
    ParameterProperties parameter,
    Map<String, List<String>> moduleCodes
) {

    /**
     * URL 패턴 목록 불변 컬렉션 복사
     */
    public AccessTraceProperties {
        enabled = enabled == null || enabled;
        apiPathPrefix = normalizeRequiredApiPathPrefix(apiPathPrefix);
        excludeUrlPatterns = excludeUrlPatterns == null ? List.of() : List.copyOf(excludeUrlPatterns);
        parameter = parameter == null ? ParameterProperties.defaults() : parameter;
        moduleCodes = copyModuleCodes(moduleCodes);
    }

    /**
     * PARAMETER 컬럼 마스킹 및 축약 설정
     *
     * @param maxLength 최종 PARAMETER 최대 길이
     * @param bodyMaskedFields body/query 마스킹 필드 규칙
     * @param bodyTruncatedFields body/query 축약 필드 규칙
     */
    public record ParameterProperties(
        Integer maxLength,
        HttpLogMatchRule bodyMaskedFields,
        HttpLogMatchRule bodyTruncatedFields
    ) {

        private static final int DEFAULT_MAX_LENGTH = 1000;

        /**
         * 필수 규칙 검증 및 기본 최대 길이 보완
         */
        public ParameterProperties {
            maxLength = maxLength == null ? DEFAULT_MAX_LENGTH : maxLength;
            if (maxLength <= 0) {
                throw new IllegalArgumentException("access-trace.parameter.max-length must be greater than 0");
            }
        }

        public HttpLogRules httpLogRules() {
            return new HttpLogRules(HttpLogMatchRule.empty(), bodyMaskedFields, bodyTruncatedFields);
        }

        /**
         * 기본 PARAMETER 마스킹 및 축약 설정
         */
        public static ParameterProperties defaults() {
            return new ParameterProperties(
                DEFAULT_MAX_LENGTH,
                emptyRule(),
                emptyRule()
            );
        }

        /**
         * 빈 매칭 규칙 생성
         */
        private static HttpLogMatchRule emptyRule() {
            return HttpLogMatchRule.empty();
        }
    }

    /**
     * 설정값 외부 변경 방지를 위한 방어 복사
     */
    private static String normalizeRequiredApiPathPrefix(String apiPathPrefix) {
        if (!StringUtils.hasText(apiPathPrefix)) {
            throw new IllegalArgumentException("access-trace.api-path-prefix is required");
        }

        String normalizedPrefix = apiPathPrefix.trim();
        StringBuilder pathPrefixBuilder = new StringBuilder(normalizedPrefix.length() + 2);
        if (!normalizedPrefix.startsWith("/")) {
            pathPrefixBuilder.append('/');
        }
        pathPrefixBuilder.append(normalizedPrefix);
        if (!normalizedPrefix.endsWith("/")) {
            pathPrefixBuilder.append('/');
        }
        return pathPrefixBuilder.toString();
    }

    private static Map<String, List<String>> copyModuleCodes(Map<String, List<String>> moduleCodes) {
        if (moduleCodes == null || moduleCodes.isEmpty()) {
            return Map.of();
        }

        Map<String, List<String>> copied = new LinkedHashMap<>();
        moduleCodes.forEach((key, values) -> copied.put(key, values == null ? List.of() : List.copyOf(values)));
        return Collections.unmodifiableMap(copied);
    }
}
