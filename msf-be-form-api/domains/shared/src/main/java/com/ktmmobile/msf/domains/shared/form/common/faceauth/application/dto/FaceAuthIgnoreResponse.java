package com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto;

public record FaceAuthIgnoreResponse(
    String resNo,
    String resultCode,
    String resultMessage,
    String transactionId
) {

    public static FaceAuthIgnoreResponse of(
        String resNo,
        String resultCode,
        String resultMessage,
        String transactionId
    ) {
        return new FaceAuthIgnoreResponse(resNo, resultCode, resultMessage, transactionId);
    }
}
