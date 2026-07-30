package com.ktmmobile.msf.commons.websecurity.web.accesstrace;

/**
 * API 요청의 처리 모듈 구분 코드 해석 포트
 */
public interface AccessTraceModuleCodeResolver {

    /**
     * HTTP 메서드와 요청 경로에 매칭되는 처리 모듈 구분 코드 반환
     *
     * @param method HTTP 메서드
     * @param path 요청 경로
     * @return 처리 모듈 구분 코드
     */
    String resolve(String method, String path);
}
