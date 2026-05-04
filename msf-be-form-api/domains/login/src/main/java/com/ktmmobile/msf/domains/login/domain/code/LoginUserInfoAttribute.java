package com.ktmmobile.msf.domains.login.domain.code;

public enum LoginUserInfoAttribute {

    DEVICE_AUTH_COMPLETED("deviceAuthCompleted"),
    AGENT_CODE("agentCode"),
    SHOP_CODE("shopCode");

    private final String key;

    LoginUserInfoAttribute(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
