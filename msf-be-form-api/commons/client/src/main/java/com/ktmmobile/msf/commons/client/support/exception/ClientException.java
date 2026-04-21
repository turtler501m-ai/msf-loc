package com.ktmmobile.msf.commons.client.support.exception;

import com.ktmmobile.msf.commons.common.exception.CommonException;

public class ClientException extends CommonException {

    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
