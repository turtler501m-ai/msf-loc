package com.ktmmobile.msf.commons.logincore.domain.code;

import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;

public enum LoginAuthType {
    PASSWORD,
    BIOPASS;

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
