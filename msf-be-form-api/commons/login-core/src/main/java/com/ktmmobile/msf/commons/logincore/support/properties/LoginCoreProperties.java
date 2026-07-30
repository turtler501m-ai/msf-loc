package com.ktmmobile.msf.commons.logincore.support.properties;

import java.time.Duration;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * login-core 공통 동작을 제어하는 설정 속성
 */
@Validated
@ConfigurationProperties(prefix = "login-core")
public record LoginCoreProperties(
    @Valid @NotNull Failure failure,
    @Valid @NotNull TwoFactor twoFactor,
    @Valid @NotNull UserInfoCache userInfoCache,
    @Valid @NotNull Biometric biometric,
    @Valid @NotNull Token token,
    @Valid @NotNull Cookie cookie,
    @Valid Policy policy
) {

    /**
     * Access Token과 Refresh Token 수명 설정
     */
    public record Token(
        @NotNull Duration accessTimeToLive,
        @NotNull Duration refreshTimeToLive
    ) {
    }


    /**
     * Refresh Token Cookie 속성 설정
     */
    public record Cookie(
        @NotNull String refreshTokenName,
        @NotNull String sameSite,
        @NotNull String path,
        @NotNull Boolean secure
    ) {
    }


    /**
     * 생체인증 challenge 수명 설정
     */
    public record Biometric(
        @NotNull Duration challengeTimeToLive,
        @Valid ChallengeCrypto challengeCrypto
    ) {

        public Biometric {
            challengeCrypto = challengeCrypto == null ? new ChallengeCrypto(null, null) : challengeCrypto;
        }
    }


    /**
     * 생체인증 challenge AES-256-CBC 복호화 설정
     */
    public record ChallengeCrypto(
        String key,
        String iv
    ) {
    }


    /**
     * 로그인 실패 제한 설정
     */
    public record Failure(
        @NotNull Integer maxCount
    ) {
    }


    /**
     * 2FA challenge와 로그인 세션 수명 설정
     */
    public record TwoFactor(
        @NotNull Duration challengeTimeToLive,
        @NotNull Duration sessionTimeToLive
    ) {
    }


    /**
     * 사용자 정보 캐시와 스탬피드 방지 설정
     */
    public record UserInfoCache(
        @NotNull Duration timeToLive,
        @NotNull Duration staleTimeToLive,
        @NotNull Duration lockTimeToLive,
        @NotNull Duration lockWaitTime,
        @NotNull Duration lockRetryInterval
    ) {
    }


    /**
     * 적용할 정책 bean 이름 whitelist 설정
     */
    public record Policy(
        List<String> completion,
        List<String> failure,
        List<String> requiredAction
    ) {

        public Policy {
            completion = completion == null ? List.of() : List.copyOf(completion);
            failure = failure == null ? List.of() : List.copyOf(failure);
            requiredAction = requiredAction == null ? List.of() : List.copyOf(requiredAction);
        }
    }
}
