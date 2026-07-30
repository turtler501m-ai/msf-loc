package com.ktmmobile.msf.commons.logincore.domain.code;

import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;

public enum LoginAuthType {
    PASSWORD,
    BIOPASS;

    /**
     * 문자열 기준 로그인 인증 유형 변환
     *
     * @param value 인증 유형 문자열
     * @return 로그인 인증 유형
     */
    public static LoginAuthType valueOfNullable(String value) {
        if (!StringUtils.hasText(value)) {
            return PASSWORD;
        }
        try {
            return LoginAuthType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new LoginException("지원하지 않는 인증방식입니다.", e);
        }
    }
}
