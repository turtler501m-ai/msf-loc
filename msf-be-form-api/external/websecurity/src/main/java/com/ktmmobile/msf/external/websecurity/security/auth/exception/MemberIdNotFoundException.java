package com.ktmmobile.msf.external.websecurity.security.auth.exception;

public class MemberIdNotFoundException extends MemberAuthenticationException {

    public MemberIdNotFoundException(String msg) {
        super(msg);
    }

    public MemberIdNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
