package com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto;

public record FaceAuthConfirmResponse(
    String resultCode,
    String resultMessage
) {

    public static FaceAuthConfirmResponse of(String code, String message, String resNo) {
        return new FaceAuthConfirmResponse(code, message);
    }
}
