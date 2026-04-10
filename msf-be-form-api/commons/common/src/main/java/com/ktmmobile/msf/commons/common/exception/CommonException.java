package com.ktmmobile.msf.commons.common.exception;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    private CustomErrorCode code;

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    protected CommonException(CustomErrorCode code) {
        this.code = code;
    }

    protected CommonException(String message, CustomErrorCode code) {
        super(message);
        this.code = code;
    }

    public System.Logger.Level getLogLevel() {
        return System.Logger.Level.ERROR;
    }
}
