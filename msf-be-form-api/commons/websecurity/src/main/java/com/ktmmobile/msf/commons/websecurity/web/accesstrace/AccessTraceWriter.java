package com.ktmmobile.msf.commons.websecurity.web.accesstrace;

/**
 * 저장소별 API 요청 이력 저장 구현 연결 포트
 */
public interface AccessTraceWriter {

    /**
     * 구성된 API 요청 이력 저장
     *
     * @param accessTrace 저장할 API 요청 이력
     */
    void write(AccessTrace accessTrace);
}
