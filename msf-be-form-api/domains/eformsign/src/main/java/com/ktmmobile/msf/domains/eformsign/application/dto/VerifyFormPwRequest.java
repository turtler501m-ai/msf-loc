package com.ktmmobile.msf.domains.eformsign.application.dto;


public record VerifyFormPwRequest(
    String password,
    String formTypeCd,
    String cstmrTypeCd,
    String requestKey
) {
}
