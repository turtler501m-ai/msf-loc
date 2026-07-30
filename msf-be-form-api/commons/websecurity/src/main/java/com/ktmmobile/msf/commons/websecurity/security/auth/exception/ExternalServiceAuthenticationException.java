package com.ktmmobile.msf.commons.websecurity.security.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class ExternalServiceAuthenticationException extends AuthenticationException {

    public ExternalServiceAuthenticationException(String msg) {
        super(msg);
    }

    public ExternalServiceAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
