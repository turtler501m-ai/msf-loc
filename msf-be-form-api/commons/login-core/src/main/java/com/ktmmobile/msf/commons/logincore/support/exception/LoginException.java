package com.ktmmobile.msf.commons.logincore.support.exception;

import com.ktmmobile.msf.commons.common.exception.DomainException;

public class LoginException extends DomainException {

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(String message) {
        super(message);
    }
}
