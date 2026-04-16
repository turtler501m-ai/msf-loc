package com.ktmmobile.msf.domains.login.domain.dto;

import java.time.Instant;

public record LoginTokens(
    String accessToken,
    Instant accessTokenExpiresAt,
    String refreshToken,
    Instant refreshTokenExpiresAt
) {
}
