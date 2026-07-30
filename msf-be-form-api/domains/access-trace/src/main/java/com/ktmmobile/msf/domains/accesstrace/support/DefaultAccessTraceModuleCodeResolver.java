package com.ktmmobile.msf.domains.accesstrace.support;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.websecurity.web.accesstrace.AccessTraceExcludeUrlMatcher;
import com.ktmmobile.msf.commons.websecurity.web.accesstrace.AccessTraceModuleCodeResolver;

/**
 * YAML 설정 기반 처리 모듈 구분 코드 해석 및 제외 URL 매칭 구현체
 */
@RequiredArgsConstructor
@Component
public class DefaultAccessTraceModuleCodeResolver implements AccessTraceModuleCodeResolver, AccessTraceExcludeUrlMatcher {

    private static final String METHOD_PATH_SEPARATOR = " ";

    private final AccessTraceProperties accessTraceProperties;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 요청 경로의 API 요청 이력 기록 대상 여부 반환
     */
    @Override
    public boolean supports(String path) {
        return !Boolean.FALSE.equals(accessTraceProperties.enabled())
            && StringUtils.hasText(path)
            && path.startsWith(accessTraceProperties.apiPathPrefix());
    }

    /**
     * HTTP 메서드와 요청 경로에 매칭되는 처리 모듈 구분 코드 반환
     */
    @Override
    public String resolve(String method, String path) {
        if (Boolean.FALSE.equals(accessTraceProperties.enabled())) {
            return "";
        }
        for (Map.Entry<String, List<String>> entry: accessTraceProperties.moduleCodes().entrySet()) {
            if (matchesAny(entry.getValue(), method, path)) {
                return entry.getKey();
            }
        }
        // 설정된 URL 패턴에 매칭되지 않은 요청은 빈 값으로 기록
        return "";
    }

    /**
     * HTTP 메서드와 요청 경로의 API 요청 이력 제외 여부 반환
     */
    @Override
    public boolean matches(String method, String path) {
        return Boolean.FALSE.equals(accessTraceProperties.enabled())
            || matchesAny(accessTraceProperties.excludeUrlPatterns(), method, path);
    }

    /**
     * 하나의 처리 모듈 코드에 등록된 URL 패턴 중 매칭 여부 확인
     */
    private boolean matchesAny(List<String> patterns, String method, String path) {
        if (patterns == null || patterns.isEmpty()) {
            return false;
        }
        return patterns.stream().anyMatch(pattern -> matches(pattern, method, path));
    }

    /**
     * URL 패턴과 현재 요청의 HTTP 메서드 및 경로 비교
     */
    private boolean matches(String pattern, String method, String path) {
        if (!StringUtils.hasText(pattern)) {
            return false;
        }

        String normalizedPattern = removeQueryString(pattern.trim());
        int separatorIndex = normalizedPattern.indexOf(METHOD_PATH_SEPARATOR);
        if (separatorIndex < 0) {
            // 메서드를 생략한 설정은 요청 경로만 Ant 패턴으로 비교
            return pathMatcher.match(normalizedPattern, path);
        }

        String patternMethod = normalizedPattern.substring(0, separatorIndex).trim();
        String patternPath = normalizedPattern.substring(separatorIndex + 1).trim();
        // 메서드 자리에 '*'를 쓰면 모든 HTTP 메서드 허용
        return ("*".equals(patternMethod) || patternMethod.equalsIgnoreCase(method))
            && pathMatcher.match(patternPath, path);
    }

    /**
     * URL 패턴에 포함된 QueryString 제거
     */
    private String removeQueryString(String value) {
        int queryIndex = value.indexOf('?');
        return queryIndex < 0 ? value : value.substring(0, queryIndex);
    }
}
