package com.ktmmobile.msf.commons.logincore.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record LoginRequiredAction(
    String code,
    String message,
    @JsonIgnore
    Boolean tokenIssuable
) {

    public static final String VERIFY_TWO_FACTOR_CODE = "VERIFY_2FA";
    public static final String DEVICE_AUTH_CODE = "DEVICE_AUTH";
    public static final String PASSWORD_CHANGE_CODE = "PASSWORD_CHANGE";

    public LoginRequiredAction {
        tokenIssuable = tokenIssuable != null && tokenIssuable;
    }

    public static LoginRequiredAction passwordChange() {
        return new LoginRequiredAction(PASSWORD_CHANGE_CODE, "비밀번호 변경이 필요합니다.", false);
    }

    public static LoginRequiredAction verifyTwoFactor() {
        return new LoginRequiredAction(VERIFY_TWO_FACTOR_CODE, "추가 인증이 필요합니다.", false);
    }

    public static LoginRequiredAction deviceAuth() {
        return new LoginRequiredAction(DEVICE_AUTH_CODE, "단말 사용 인증이 필요합니다.", false);
    }

    @JsonIgnore
    public boolean isVerifyTwoFactor() {
        return VERIFY_TWO_FACTOR_CODE.equals(code);
    }
}
