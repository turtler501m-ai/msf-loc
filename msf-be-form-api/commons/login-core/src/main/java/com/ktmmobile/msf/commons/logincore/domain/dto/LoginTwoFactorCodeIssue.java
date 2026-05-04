package com.ktmmobile.msf.commons.logincore.domain.dto;

public record LoginTwoFactorCodeIssue(
    LoginSessionUser principal,
    String verificationCode
) {
}
