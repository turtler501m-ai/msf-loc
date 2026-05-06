package com.ktmmobile.msf.commons.logincore.domain.dto;

public record LoginTwoFactorCompletionResult(
    String loginSessionId,
    boolean completed
) {
}
