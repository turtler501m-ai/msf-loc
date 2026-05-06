package com.ktmmobile.msf.commons.logincore.domain.dto;

public record LoginTwoFactorStatus(
    boolean sessionExists,
    boolean twoFactorCompleted
) {

    public static LoginTwoFactorStatus notFound() {
        return new LoginTwoFactorStatus(false, false);
    }
}
