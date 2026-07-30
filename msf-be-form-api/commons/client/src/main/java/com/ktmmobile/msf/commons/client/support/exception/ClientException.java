package com.ktmmobile.msf.commons.client.support.exception;

import com.ktmmobile.msf.commons.common.exception.CommonException;

/**
 * HTTP client 호출 실패 공통 예외
 */
public class ClientException extends CommonException {

    /**
     * 메시지 기반 HTTP client 예외 생성
     */
    public ClientException(String message) {
        super(message, (Throwable) null, false);
    }

    /**
     * 메시지와 원인 예외 기반 HTTP client 예외 생성
     */
    public ClientException(String message, Throwable cause) {
        super(message, cause, false);
    }
}
