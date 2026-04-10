package com.ktmmobile.msf.commons.common.exception;

/**
 * DomainException 하위 클래스는 API 응답 시 Exception 메시지가 직접 반환됩니다.
 * @see SimpleDomainException
 */
public abstract class DomainException extends CommonException {

    protected DomainException(String message) {
        super(message);
    }

    protected DomainException(String message, Throwable cause) {
        super(message, cause);
    }

    protected DomainException(CustomErrorCode code) {
        super(code);
    }

    protected DomainException(String message, CustomErrorCode code) {
        super(message, code);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
