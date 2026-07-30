package com.ktmmobile.msf.commons.logincore.domain.entity;

/**
 * 로그인 인증과 정책 판단에 필요한 최소 사용자 정보
 */
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
