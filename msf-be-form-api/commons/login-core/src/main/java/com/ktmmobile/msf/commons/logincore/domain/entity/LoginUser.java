package com.ktmmobile.msf.commons.logincore.domain.entity;

public record LoginUser(
    String userId,
    String userName,
    String phoneNumber,
    String encodedPassword,
    boolean enabled,
    int loginFailCount,
    boolean passwordChangeRequired,
    String allowedClientIps
) {
}
