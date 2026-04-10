package com.ktmmobile.msf.commons.login.application.port.out;

import java.time.Duration;

public interface JwtTokenIssuer {

    IssuedJwtToken issueAccessToken(String userId, Duration timeToLive);

    IssuedJwtToken issueRefreshToken(String userId, Duration timeToLive);

    record IssuedJwtToken(
        String tokenValue,
        String jti,
        java.time.Instant expiresAt
    ) {
    }
}
