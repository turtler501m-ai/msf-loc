package com.ktmmobile.msf.commons.logincore.application.service;

import java.time.Duration;

import jakarta.servlet.http.Cookie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.AuthenticationException;

import com.ktmmobile.msf.commons.logincore.support.exception.RefreshTokenNotExistsException;
import com.ktmmobile.msf.commons.logincore.support.properties.LoginCoreProperties;
import com.ktmmobile.msf.commons.websecurity.security.auth.service.LoginJwtTokenValidator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Refresh Token 쿠키 서비스")
class LoginRefreshTokenCookieServiceTest {

    private static final String REFRESH_TOKEN_NAME = "refreshToken";

    private final LoginRefreshTokenCookieService service = new LoginRefreshTokenCookieService(properties());

    @Test
    @DisplayName("Refresh Token 쿠키가 없으면 인증 예외를 던진다")
    void getRefreshTokenThrowsAuthenticationExceptionWhenTokenIsMissing() {
        assertThatThrownBy(() -> service.getRefreshToken(null))
            .isInstanceOf(RefreshTokenNotExistsException.class)
            .isInstanceOf(AuthenticationException.class)
            .hasMessage(LoginJwtTokenValidator.TOKEN_LOGGED_OUT_MESSAGE);
    }

    @Test
    @DisplayName("Refresh Token 쿠키 값을 조회한다")
    void getRefreshTokenReturnsCookieValue() {
        Cookie[] cookies = {
            new Cookie(REFRESH_TOKEN_NAME, "refresh-token-value")
        };

        assertThat(service.getRefreshToken(cookies)).isEqualTo("refresh-token-value");
    }

    private static LoginCoreProperties properties() {
        return new LoginCoreProperties(
            new LoginCoreProperties.Failure(3),
            new LoginCoreProperties.TwoFactor(Duration.ofMinutes(3), Duration.ofMinutes(10)),
            new LoginCoreProperties.UserInfoCache(
                Duration.ofDays(1),
                Duration.ofHours(1),
                Duration.ofSeconds(3),
                Duration.ofSeconds(1),
                Duration.ofMillis(100)
            ),
            new LoginCoreProperties.Biometric(Duration.ofMinutes(3), null),
            new LoginCoreProperties.Token(Duration.ofMinutes(5), Duration.ofHours(1)),
            new LoginCoreProperties.Cookie(REFRESH_TOKEN_NAME, "Strict", "/api/n/auth/refresh", true),
            new LoginCoreProperties.Policy(null, null, null)
        );
    }
}
