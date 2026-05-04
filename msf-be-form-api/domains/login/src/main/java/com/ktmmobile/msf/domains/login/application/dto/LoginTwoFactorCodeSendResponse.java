package com.ktmmobile.msf.domains.login.application.dto;

import java.time.LocalDateTime;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorCodeResult;

public record LoginTwoFactorCodeSendResponse(
    String loginSessionId,
    LocalDateTime twoFactorExpiresAt
) {

    public static LoginTwoFactorCodeSendResponse from(LoginTwoFactorCodeResult result) {
        return new LoginTwoFactorCodeSendResponse(
            result.loginSessionId(),
            result.twoFactorExpiresAt()
        );
    }
}
