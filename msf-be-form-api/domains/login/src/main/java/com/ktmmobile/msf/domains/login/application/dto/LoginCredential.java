package com.ktmmobile.msf.domains.login.application.dto;

import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.logincore.domain.code.LoginAuthType;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;

public record LoginCredential(
    String userId,
    String password,
    String deviceUuid,
    LoginAuthType authType
) implements LoginAuthenticationCredential {

    @Override
    public UserType userType() {
        return UserType.FORM_USER;
    }

    @Override
    public boolean isPasswordAuth() {
        return authType == LoginAuthType.PASSWORD;
    }

    @Override
    public boolean isDeviceAuth() {
        return authType == LoginAuthType.BIOPASS;
    }
}
