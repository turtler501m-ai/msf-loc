package com.ktmmobile.msf.commons.common.exception;

/**
 * DomainException 하위 클래스는 API 응답 시 Exception 메시지가 직접 반환됩니다.
 * @see SimpleDomainException
 */
public abstract class DomainException extends CommonException {

    protected DomainException(String message) {
        super(message, (Throwable) null, false);
    }

    protected DomainException(String message, Throwable cause) {
        super(message, cause, false);
    }

    protected DomainException(CustomErrorCode code) {
        super(null, code, false);
    }

    protected DomainException(String message, CustomErrorCode code) {
        super(message, code, false);
    }
}
