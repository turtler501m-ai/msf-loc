package com.ktmmobile.msf.domains.login.application.dto;

import jakarta.validation.constraints.NotBlank;

import com.ktmmobile.msf.commons.logincore.domain.code.LoginAuthType;

public record LoginAuthRequest(
    @NotBlank String userId,
    String password,
    String deviceUuid,
    String authType
) {

    public LoginCredential toCredential() {
        LoginAuthType resolvedAuthType = LoginAuthType.valueOfNullable(authType);
        return new LoginCredential(userId, password, deviceUuid, resolvedAuthType);
    }
}
