package com.ktmmobile.msf.commons.logincore.domain.dto;

import java.util.LinkedHashMap;
import java.util.Map;

public record LoginResultUserInfo(
    String userId,
    String userName,
    String phoneNumber,
    Map<String, Object> attributes
) {

    public LoginResultUserInfo {
        attributes = attributes == null ? new LinkedHashMap<>() : new LinkedHashMap<>(attributes);
    }

    public static LoginResultUserInfo from(LoginActionRequired required) {
        return new LoginResultUserInfo(
            required.userId(),
            required.userName(),
            required.phoneNumber(),
            required.attributes()
        );
    }

    public static LoginResultUserInfo from(LoginSessionReady ready) {
        return new LoginResultUserInfo(
            ready.userId(),
            ready.userName(),
            ready.phoneNumber(),
            ready.attributes()
        );
    }

    public static LoginResultUserInfo from(LoginTwoFactorRequired required) {
        return new LoginResultUserInfo(
            required.userId(),
            required.userName(),
            required.phoneNumber(),
            required.attributes()
        );
    }

    public static LoginResultUserInfo from(LoginTokenPair tokenPair) {
        return new LoginResultUserInfo(
            tokenPair.userId(),
            tokenPair.userName(),
            tokenPair.phoneNumber(),
            tokenPair.attributes()
        );
    }
}
