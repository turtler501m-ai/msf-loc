package com.ktmmobile.msf.commons.common.exception;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    private final transient CustomErrorCode code;

    public CommonException(String message) {
        super(message);
        this.code = null;
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
        this.code = null;
    }

    protected CommonException(String message, Throwable cause, boolean writableStackTrace) {
        super(message, cause, true, writableStackTrace);
        this.code = null;
    }

    protected CommonException(CustomErrorCode code) {
        this.code = code;
    }

    protected CommonException(String message, CustomErrorCode code) {
        super(message);
        this.code = code;
    }

    protected CommonException(String message, CustomErrorCode code, boolean writableStackTrace) {
        super(message, null, true, writableStackTrace);
        this.code = code;
    }

    public ExceptionLogLevel getLogLevel() {
        return ExceptionLogLevel.ERROR;
    }
}
