package com.ktmmobile.msf.commons.logincore.domain.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LoginResultResponse<T>(
    boolean tokenIssuable,
    RequiredAction requiredAction,
    String loginSessionId,
    LocalDateTime twoFactorExpiresAt,
    T userInfo,
    String accessToken,
    LocalDateTime accessTokenExpiresAt,
    LocalDateTime refreshTokenExpiresAt
) {

    /**
     * 로그인 결과 응답 변환
     *
     * @param result 로그인 결과
     * @param userInfoMapper 사용자 정보 응답 변환 함수
     * @return 로그인 결과 응답
     */
    public static <T> LoginResultResponse<T> from(LoginResult result, Function<LoginResultUserInfo, T> userInfoMapper) {
        return switch (result) {
            case LoginTokenIssued issued -> from(issued.tokenPair(), userInfoMapper);
            case LoginActionRequired required -> new LoginResultResponse<>(
                tokenIssuable(required.requiredActions()),
                RequiredAction.from(required.requiredActions()),
                required.loginSessionId(),
                null,
                userInfoMapper.apply(LoginResultUserInfo.from(required)),
                null,
                null,
                null
            );
            case LoginSessionReady ready -> new LoginResultResponse<>(
                true,
                RequiredAction.empty(),
                ready.loginSessionId(),
                null,
                userInfoMapper.apply(LoginResultUserInfo.from(ready)),
                null,
                null,
                null
            );
            case LoginTwoFactorRequired required -> new LoginResultResponse<>(
                false,
                RequiredAction.from(required.requiredActions()),
                required.loginSessionId(),
                required.expiresAt(),
                userInfoMapper.apply(LoginResultUserInfo.from(required)),
                null,
                null,
                null
            );
        };
    }

    /**
     * 토큰 쌍 응답 변환
     *
     * @param tokenPair 토큰 쌍
     * @param userInfoMapper 사용자 정보 응답 변환 함수
     * @return 로그인 결과 응답
     */
    public static <T> LoginResultResponse<T> from(LoginTokenPair tokenPair, Function<LoginResultUserInfo, T> userInfoMapper) {
        return new LoginResultResponse<>(
            true,
            RequiredAction.from(tokenPair.requiredActions()),
            null,
            null,
            userInfoMapper.apply(LoginResultUserInfo.from(tokenPair)),
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
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).withNano(0);
    }

    /**
     * 토큰 발급 가능 여부 계산
     *
     * @param actions 필수 조치 목록
     * @return 토큰 발급 가능 여부
     */
    private static boolean tokenIssuable(List<LoginRequiredAction> actions) {
        List<LoginRequiredAction> safeActions = actions == null ? List.of() : actions;
        return safeActions.stream().allMatch(LoginRequiredAction::tokenIssuable);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record RequiredAction(
        boolean exists,
        String actionCode,
        String actionMessage
    ) {

        /**
         * 필수 조치 응답 변환
         *
         * @param actions 필수 조치 목록
         * @return 필수 조치 응답
         */
        public static RequiredAction from(List<LoginRequiredAction> actions) {
            List<LoginRequiredAction> safeActions = actions == null ? List.of() : List.copyOf(actions);
            return safeActions.stream()
                .findFirst()
                .map(action -> new RequiredAction(true, action.code(), action.message()))
                .orElseGet(RequiredAction::empty);
        }

        /**
         * 빈 필수 조치 응답 생성
         *
         * @return 빈 필수 조치 응답
         */
        public static RequiredAction empty() {
            return new RequiredAction(false, null, null);
        }
    }

}
