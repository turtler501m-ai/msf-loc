package com.ktmmobile.msf.domains.login.application.port.out;

import java.time.Instant;

public interface JwtTokenReader {

    DecodedJwtToken read(String tokenValue);

    record DecodedJwtToken(
        String tokenValue,
        String userId,
        String jti,
        String type,
        Instant expiresAt
    ) {
    }
}
