package com.ktmmobile.msf.commons.logincore.domain.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public record LoginResponse(
    String userId,
    String userName,
    String phoneNumber,
    List<LoginRequiredAction> requiredActions,
    String accessToken,
    LocalDateTime accessTokenExpiresAt,
    LocalDateTime refreshTokenExpiresAt
) {

    private static final ZoneId RESPONSE_ZONE = ZoneId.systemDefault();

    public static LoginResponse from(LoginTokenPair tokenPair) {
        return new LoginResponse(
            tokenPair.userId(),
            tokenPair.userName(),
            tokenPair.phoneNumber(),
            tokenPair.requiredActions(),
            tokenPair.accessToken(),
            toResponseDateTime(tokenPair.accessTokenExpiresAt()),
            toResponseDateTime(tokenPair.refreshTokenExpiresAt())
        );
    }

    private static LocalDateTime toResponseDateTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, RESPONSE_ZONE).withNano(0);
    }
}
