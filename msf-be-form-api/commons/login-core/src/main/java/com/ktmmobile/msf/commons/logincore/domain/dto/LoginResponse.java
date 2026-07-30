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

    /**
     * 토큰 쌍 응답 변환
     *
     * @param tokenPair 토큰 쌍
     * @return 로그인 응답
     */
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

    /**
     * 응답 일시 변환
     *
     * @param instant 기준 Instant
     * @return 응답 일시
     */
    private static LocalDateTime toResponseDateTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, RESPONSE_ZONE).withNano(0);
    }
}
