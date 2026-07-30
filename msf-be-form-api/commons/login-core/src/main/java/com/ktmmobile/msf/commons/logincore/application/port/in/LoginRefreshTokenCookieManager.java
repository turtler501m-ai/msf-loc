package com.ktmmobile.msf.commons.logincore.application.port.in;

import jakarta.servlet.http.Cookie;

import org.springframework.http.ResponseCookie;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTokenPair;

/**
 * Refresh Token Cookie 생성과 조회 포트
 */
public interface LoginRefreshTokenCookieManager {

    /**
     * Cookie 배열에서 Refresh Token 필수 조회
     *
     * @param cookies 요청 Cookie 배열
     * @return Refresh Token
     */
    String getRefreshToken(Cookie[] cookies);

    /**
     * Cookie 배열에서 Refresh Token 선택 조회
     *
     * @param cookies 요청 Cookie 배열
     * @return Refresh Token
     */
    String findRefreshToken(Cookie[] cookies);

    /**
     * Refresh Token 저장 Cookie 생성
     *
     * @param tokenPair 발급된 토큰 쌍
     * @return 응답 Cookie
     */
    ResponseCookie createRefreshTokenCookie(LoginTokenPair tokenPair);

    /**
     * Refresh Token 삭제 Cookie 생성
     *
     * @return 응답 Cookie
     */
    ResponseCookie deleteRefreshTokenCookie();
}
