package com.ktmmobile.msf.commons.logincore.application.dto;

import jakarta.validation.constraints.NotBlank;

public record BiometricChallengeRequest(
    @NotBlank String deviceUuid
) {
}
