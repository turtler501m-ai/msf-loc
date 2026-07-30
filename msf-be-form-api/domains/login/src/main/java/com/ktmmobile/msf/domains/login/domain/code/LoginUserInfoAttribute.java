package com.ktmmobile.msf.domains.login.domain.code;

public enum LoginUserInfoAttribute {

    DEVICE_AUTH_COMPLETED("deviceAuthCompleted"),
    DEVICE_UUID("deviceUuid");

    private final String key;

    LoginUserInfoAttribute(String key) {
        this.key = key;
    }

    /**
     * 사용자 정보 속성 키 조회
     *
     * @return 속성 키
     */
    public String key() {
        return key;
    }
}
