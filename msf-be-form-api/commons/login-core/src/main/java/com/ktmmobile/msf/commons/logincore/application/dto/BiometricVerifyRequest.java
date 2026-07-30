package com.ktmmobile.msf.commons.logincore.application.dto;

import jakarta.validation.constraints.NotBlank;

public record BiometricVerifyRequest(
    @NotBlank String deviceUuid,
    @NotBlank String bioKey,
    @NotBlank String encryptedNonce,
    String osCd,
    String bioLoginYn,
    String bioLoginToken
) {
}
