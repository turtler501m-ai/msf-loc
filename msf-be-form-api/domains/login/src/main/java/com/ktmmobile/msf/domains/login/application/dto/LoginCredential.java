package com.ktmmobile.msf.domains.login.application.dto;

import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.logincore.domain.code.LoginAuthType;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;

public record LoginCredential(
    String userId,
    String password,
    String deviceUuid,
    LoginAuthType authType,
    String clientIp
) implements LoginAuthenticationCredential {

    /**
     * 로그인 사용자 유형 조회
     *
     * @return 사용자 유형
     */
    @Override
    public UserType userType() {
        return UserType.FORM_USER;
    }

    /**
     * 비밀번호 인증 여부 확인
     *
     * @return 비밀번호 인증 여부
     */
    @Override
    public boolean isPasswordAuth() {
        return authType == LoginAuthType.PASSWORD;
    }

    /**
     * 단말 인증 여부 확인
     *
     * @return 단말 인증 여부
     */
    @Override
    public boolean isDeviceAuth() {
        return authType == LoginAuthType.BIOPASS;
    }
}
