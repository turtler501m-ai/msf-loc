package com.ktmmobile.msf.commons.login.domain.dto;

import java.time.Instant;

public record LoginTokens(
    String accessToken,
    Instant accessTokenExpiresAt,
    String refreshToken,
    Instant refreshTokenExpiresAt
) {
}
