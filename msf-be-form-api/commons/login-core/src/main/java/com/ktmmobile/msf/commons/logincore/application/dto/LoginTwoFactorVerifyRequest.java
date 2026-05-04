package com.ktmmobile.msf.commons.logincore.application.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginTwoFactorVerifyRequest(
    @NotBlank
    String loginSessionId,

    @NotBlank
    String verificationCode
) {
}
