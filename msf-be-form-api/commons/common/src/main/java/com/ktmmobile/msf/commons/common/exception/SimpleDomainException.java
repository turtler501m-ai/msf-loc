package com.ktmmobile.msf.commons.common.exception;

/**
 * DomainException의 기본 구현 클래스입니다.
 * 이 Exception을 throw 하면 API 응답 시 Exception 메시지가 직접 반환됩니다.
 * @see DomainException
 */
public class SimpleDomainException extends DomainException {

    public SimpleDomainException(String message) {
        super(message);
    }

    public SimpleDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
