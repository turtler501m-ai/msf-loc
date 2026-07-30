package com.ktmmobile.msf.domains.eformsign.feature.application.dto;


public record VerifyFormPwRequest(
    String password,
    String formType,
    String requestKey
) {
}
