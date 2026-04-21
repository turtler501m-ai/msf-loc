package com.ktmmobile.msf.commons.file.support.exception;

import com.ktmmobile.msf.commons.common.exception.CommonException;

public class FileServiceException extends CommonException {

    public FileServiceException(String message) {
        super(message);
    }

    public FileServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
