package com.ktmmobile.msf.commons.logincore.domain.dto;

import java.time.Instant;

public record LoginBiometricChallenge(
    String challengeId,
    String nonce,
    Instant expiresAt
) {
}
