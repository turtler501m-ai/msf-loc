package com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FaceAuthSmsResponse(
    String resultCode,
    String resultMessage,
    String resNo,
    String transactionId,
    String formId,
    Boolean result,
    Long seconds
) {

    public static FaceAuthSmsResponse of(FaceAuthUrlResponse response, Boolean result) {
        return new FaceAuthSmsResponse(
            response.resultCode(),
            response.resultMessage(),
            response.resNo(),
            response.transactionId(),
            response.formId(),
            result,
            response.seconds()
        );
    }
}
