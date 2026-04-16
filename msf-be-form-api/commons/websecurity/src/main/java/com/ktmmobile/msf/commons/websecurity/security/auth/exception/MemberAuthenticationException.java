package com.ktmmobile.msf.commons.websecurity.security.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class MemberAuthenticationException extends AuthenticationException {

    public MemberAuthenticationException(String msg) {
        super(msg);
    }

    public MemberAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
