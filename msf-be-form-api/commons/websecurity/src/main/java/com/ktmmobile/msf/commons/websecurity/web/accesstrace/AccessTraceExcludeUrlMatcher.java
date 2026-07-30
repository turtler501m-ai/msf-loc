package com.ktmmobile.msf.commons.websecurity.web.accesstrace;

/**
 * API 요청 이력 제외 URL 매칭 포트
 */
public interface AccessTraceExcludeUrlMatcher {

    /**
     * 요청 경로의 API 요청 이력 기록 대상 여부 반환
     *
     * @param path 요청 경로
     * @return 기록 대상 여부
     */
    boolean supports(String path);

    /**
     * HTTP 메서드와 요청 경로의 API 요청 이력 제외 여부 반환
     *
     * @param method HTTP 메서드
     * @param path 요청 경로
     * @return 제외 대상 여부
     */
    boolean matches(String method, String path);
}
