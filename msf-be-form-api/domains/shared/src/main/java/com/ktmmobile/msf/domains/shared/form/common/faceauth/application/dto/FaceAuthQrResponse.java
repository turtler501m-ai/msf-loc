package com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FaceAuthQrResponse(
    String resultCode,
    String resultMessage,
    String resNo,
    String transactionId,
    String formId,
    String qr,
    Long seconds
) {

    public static FaceAuthQrResponse of(FaceAuthUrlResponse response, String qr) {
        return new FaceAuthQrResponse(
            response.resultCode(),
            response.resultMessage(),
            response.resNo(),
            response.transactionId(),
            response.formId(),
            qr,
            response.seconds() > 50 ? 50 : response.seconds()
        );
    }
}
