package com.ktmmobile.msf.commons.websecurity.security.auth.exception;

import org.springframework.security.access.AccessDeniedException;

public class MemberAccessDeniedException extends AccessDeniedException {

    public MemberAccessDeniedException(String msg) {
        super(msg);
    }

    public MemberAccessDeniedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
