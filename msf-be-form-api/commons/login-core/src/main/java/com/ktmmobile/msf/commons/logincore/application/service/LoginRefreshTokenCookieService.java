package com.ktmmobile.msf.commons.logincore.application.service;

import java.util.Arrays;
import jakarta.servlet.http.Cookie;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.logincore.application.port.in.LoginRefreshTokenCookieManager;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTokenPair;
import com.ktmmobile.msf.commons.logincore.support.exception.RefreshTokenNotExistsException;
import com.ktmmobile.msf.commons.logincore.support.properties.LoginCoreProperties;
import com.ktmmobile.msf.commons.websecurity.security.auth.service.LoginJwtTokenValidator;

@RequiredArgsConstructor
@Service
public class LoginRefreshTokenCookieService implements LoginRefreshTokenCookieManager {

    private final LoginCoreProperties properties;

    /**
     * Refresh Token 쿠키 조회
     *
     * @param cookies 쿠키 배열
     * @return Refresh Token
     */
    @Override
    public String getRefreshToken(Cookie[] cookies) {
        String refreshToken = findRefreshToken(cookies);
        if (refreshToken == null) {
            throw new RefreshTokenNotExistsException(LoginJwtTokenValidator.TOKEN_LOGGED_OUT_MESSAGE);
        }
        return refreshToken;
    }

    /**
     * Refresh Token 쿠키 Optional 조회
     *
     * @param cookies 쿠키 배열
     * @return Refresh Token
     */
    @Override
    public String findRefreshToken(Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }
        String cookieName = properties.cookie().refreshTokenName();
        return Arrays.stream(cookies)
            .filter(cookie -> cookieName.equals(cookie.getName()))
            .map(Cookie::getValue)
            .filter(StringUtils::hasText)
            .findFirst()
            .orElse(null);
    }

    /**
     * Refresh Token 쿠키 생성
     *
     * @param tokenPair 토큰 쌍
     * @return 응답 쿠키
     */
    @Override
    public ResponseCookie createRefreshTokenCookie(LoginTokenPair tokenPair) {
        LoginCoreProperties.Cookie cookie = properties.cookie();
        return ResponseCookie.from(cookie.refreshTokenName(), tokenPair.refreshToken())
            .httpOnly(true)
            .secure(cookie.secure())
            .sameSite(cookie.sameSite())
            .path(cookie.path())
            .maxAge(properties.token().refreshTimeToLive())
            .build();
    }

    /**
     * Refresh Token 삭제 쿠키 생성
     *
     * @return 응답 쿠키
     */
    @Override
    public ResponseCookie deleteRefreshTokenCookie() {
        LoginCoreProperties.Cookie cookie = properties.cookie();
        return ResponseCookie.from(cookie.refreshTokenName(), "")
            .httpOnly(true)
            .secure(cookie.secure())
            .sameSite(cookie.sameSite())
            .path(cookie.path())
            .maxAge(0)
            .build();
    }
}
