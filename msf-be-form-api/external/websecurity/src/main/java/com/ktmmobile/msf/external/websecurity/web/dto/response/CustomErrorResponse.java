package com.ktmmobile.msf.external.websecurity.web.dto.response;

import lombok.Getter;

import com.ktmmobile.msf.commons.common.exception.CustomErrorCode;

@Getter
public class CustomErrorResponse {

    private final String errorCode;
    private final String errorMessage;

    public CustomErrorResponse(CustomErrorCode customErrorCode) {
        this(customErrorCode.getErrorCode(), customErrorCode.getErrorMessage());
    }

    private CustomErrorResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
