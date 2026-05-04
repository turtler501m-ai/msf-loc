package com.ktmmobile.msf.commons.logincore.domain.dto;

import java.time.LocalDateTime;

public record VerificationCodeIssue(
    String verificationId,
    String verificationCode,
    LocalDateTime expiresAt
) {
}
