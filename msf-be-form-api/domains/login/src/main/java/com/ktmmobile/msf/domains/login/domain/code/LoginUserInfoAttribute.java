package com.ktmmobile.msf.domains.login.domain.code;

public enum LoginUserInfoAttribute {

    DEVICE_AUTH_COMPLETED("deviceAuthCompleted"),
    DEVICE_UUID("deviceUuid");

    private final String key;

    LoginUserInfoAttribute(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
