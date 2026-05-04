package com.ktmmobile.msf.commons.logincore.domain.dto;

import java.time.LocalDateTime;

public record LoginTwoFactorCodeResult(
    String loginSessionId,
    LocalDateTime twoFactorExpiresAt,
    String verificationCode
) {
}
