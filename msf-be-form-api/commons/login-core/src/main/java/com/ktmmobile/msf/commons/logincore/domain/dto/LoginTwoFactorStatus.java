package com.ktmmobile.msf.commons.logincore.domain.dto;

public record LoginTwoFactorStatus(
    boolean sessionExists,
    boolean twoFactorCompleted
) {

    /**
     * 로그인 세션 없음 상태 생성
     *
     * @return 2FA 상태
     */
    public static LoginTwoFactorStatus notFound() {
        return new LoginTwoFactorStatus(false, false);
    }
}
