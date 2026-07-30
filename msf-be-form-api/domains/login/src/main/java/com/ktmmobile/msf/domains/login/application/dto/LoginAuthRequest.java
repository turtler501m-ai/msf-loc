package com.ktmmobile.msf.domains.login.application.dto;

import jakarta.validation.constraints.NotBlank;

import com.ktmmobile.msf.commons.logincore.domain.code.LoginAuthType;

public record LoginAuthRequest(
    @NotBlank String userId,
    String password,
    @NotBlank String deviceUuid,
    String authType
) {

    /**
     * 로그인 요청 Credential 변환
     *
     * @param clientIp 클라이언트 IP
     * @return 로그인 Credential
     */
    public LoginCredential toCredential(String clientIp) {
        LoginAuthType resolvedAuthType = LoginAuthType.valueOfNullable(authType);
        return new LoginCredential(userId, password, deviceUuid, resolvedAuthType, clientIp);
    }
}
