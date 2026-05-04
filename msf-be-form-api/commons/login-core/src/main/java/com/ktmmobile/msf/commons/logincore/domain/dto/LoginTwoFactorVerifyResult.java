package com.ktmmobile.msf.commons.logincore.domain.dto;

public record LoginTwoFactorVerifyResult(
    String loginSessionId,
    boolean verified
) {
}
