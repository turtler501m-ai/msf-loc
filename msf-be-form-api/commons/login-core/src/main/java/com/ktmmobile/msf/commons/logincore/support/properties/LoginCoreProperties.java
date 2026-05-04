package com.ktmmobile.msf.commons.logincore.support.properties;

import java.time.Duration;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

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

    public record Token(
        @NotNull Duration accessTimeToLive,
        @NotNull Duration refreshTimeToLive
    ) {
    }


    public record Cookie(
        @NotNull String refreshTokenName,
        @NotNull String sameSite,
        @NotNull String path,
        @NotNull Boolean secure
    ) {
    }


    public record Biometric(
        @NotNull Duration challengeTimeToLive
    ) {
    }


    public record Failure(
        @NotNull Integer maxCount
    ) {
    }


    public record TwoFactor(
        @NotNull Duration challengeTimeToLive,
        @NotNull Duration sessionTimeToLive
    ) {
    }


    public record UserInfoCache(
        @NotNull Duration timeToLive,
        @NotNull Duration staleTimeToLive,
        @NotNull Duration lockTimeToLive,
        @NotNull Duration lockWaitTime,
        @NotNull Duration lockRetryInterval
    ) {
    }


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
