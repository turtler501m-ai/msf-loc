package com.ktmmobile.msf.commons.logincore.domain.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;

public record BiometricChallengeResponse(
    String nonce,
    LocalDateTime expiresAt
) {

    public static BiometricChallengeResponse from(LoginBiometricChallenge challenge) {
        return new BiometricChallengeResponse(
            challenge.nonce(),
            LocalDateTime.ofInstant(challenge.expiresAt(), ZoneId.systemDefault())
        );
    }
}
