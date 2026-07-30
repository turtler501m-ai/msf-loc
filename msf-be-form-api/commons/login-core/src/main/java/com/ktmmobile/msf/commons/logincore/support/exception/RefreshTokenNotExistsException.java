package com.ktmmobile.msf.commons.logincore.support.exception;

import com.ktmmobile.msf.commons.websecurity.security.auth.exception.MemberAuthenticationException;

public class RefreshTokenNotExistsException extends MemberAuthenticationException {

    /**
     * Refresh Token 없음 예외 생성
     *
     * @param message 예외 메시지
     * @param cause 원인 예외
     */
    public RefreshTokenNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Refresh Token 없음 예외 생성
     *
     * @param message 예외 메시지
     */
    public RefreshTokenNotExistsException(String message) {
        super(message);
    }
}
