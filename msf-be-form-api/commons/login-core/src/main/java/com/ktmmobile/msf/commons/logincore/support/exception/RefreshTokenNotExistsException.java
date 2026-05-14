package com.ktmmobile.msf.commons.logincore.support.exception;

import com.ktmmobile.msf.commons.common.exception.ExceptionLogLevel;

public class RefreshTokenNotExistsException extends LoginException {

    public RefreshTokenNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefreshTokenNotExistsException(String message) {
        super(message);
    }

    @Override
    public ExceptionLogLevel getLogLevel() {
        return ExceptionLogLevel.WARN;
    }
}
