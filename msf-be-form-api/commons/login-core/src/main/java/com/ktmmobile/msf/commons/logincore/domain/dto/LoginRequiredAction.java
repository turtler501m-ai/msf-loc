package com.ktmmobile.msf.commons.logincore.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 로그인 완료 전후 사용자에게 요구할 단일 조치
 */
public record LoginRequiredAction(
    String code,
    String message,
    @JsonIgnore
    Boolean tokenIssuable
) {

    // 2FA 인증 조치
    public static final String VERIFY_TWO_FACTOR_CODE = "VERIFY_2FA";

    // 단말 인증 조치
    public static final String DEVICE_AUTH_CODE = "DEVICE_AUTH";

    // 비밀번호 변경 조치
    public static final String PASSWORD_CHANGE_CODE = "PASSWORD_CHANGE";

    public LoginRequiredAction {
        tokenIssuable = tokenIssuable != null && tokenIssuable;
    }

    /**
     * 비밀번호 변경 조치 생성
     *
     * @return 비밀번호 변경 조치
     */
    public static LoginRequiredAction passwordChange() {
        return new LoginRequiredAction(PASSWORD_CHANGE_CODE, "비밀번호 변경이 필요합니다.", false);
    }

    /**
     * 2FA 인증 조치 생성
     *
     * @return 2FA 인증 조치
     */
    public static LoginRequiredAction verifyTwoFactor() {
        return new LoginRequiredAction(VERIFY_TWO_FACTOR_CODE, "추가 인증이 필요합니다.", false);
    }

    /**
     * 단말 인증 조치 생성
     *
     * @return 단말 인증 조치
     */
    public static LoginRequiredAction deviceAuth() {
        return new LoginRequiredAction(DEVICE_AUTH_CODE, "단말 사용 인증이 필요합니다.", false);
    }

    /**
     * 2FA 인증 조치 여부 확인
     *
     * @return 2FA 인증 조치 여부
     */
    @JsonIgnore
    public boolean isVerifyTwoFactor() {
        return VERIFY_TWO_FACTOR_CODE.equals(code);
    }
}
