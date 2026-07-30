package com.ktmmobile.msf.commons.logincore.support.exception;

import com.ktmmobile.msf.commons.common.exception.DomainException;
import com.ktmmobile.msf.commons.common.exception.ExceptionLogLevel;

public class LoginException extends DomainException {

    /**
     * 로그인 예외 생성
     *
     * @param message 예외 메시지
     * @param cause 원인 예외
     */
    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 로그인 예외 생성
     *
     * @param message 예외 메시지
     */
    public LoginException(String message) {
        super(message);
    }

    @Override
    public ExceptionLogLevel getLogLevel() {
        return ExceptionLogLevel.WARN;
    }
}
