package com.ktmmobile.msf.commons.logincore.application.port.in;

import jakarta.servlet.http.Cookie;

import org.springframework.http.ResponseCookie;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTokenPair;

public interface LoginRefreshTokenCookieManager {

    String getRefreshToken(Cookie[] cookies);

    String findRefreshToken(Cookie[] cookies);

    ResponseCookie createRefreshTokenCookie(LoginTokenPair tokenPair);

    ResponseCookie deleteRefreshTokenCookie();
}
