package com.ktmmobile.msf.commons.logincore.domain.dto;

public record LoginSessionState(
    LoginSessionUser principal,
    boolean twoFactorCompleted
) {
}
