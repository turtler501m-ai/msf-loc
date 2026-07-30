package com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FaceAuthUrlResponse(
    String resultCode,
    String resultMessage,
    String resNo,
    String transactionId,
    String formId,
    String url,
    Long seconds
) {

    public static FaceAuthUrlResponse of(String code, String message, String resNo, String transactionId, String formId, String url, Long seconds) {
        return new FaceAuthUrlResponse(code, message, resNo, transactionId, formId, url, seconds);
    }
}
