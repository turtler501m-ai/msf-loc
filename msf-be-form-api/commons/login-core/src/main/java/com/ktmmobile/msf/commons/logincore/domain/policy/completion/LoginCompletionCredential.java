package com.ktmmobile.msf.commons.logincore.domain.policy.completion;

public interface LoginCompletionCredential {

    String password();

    String deviceUuid();

    boolean isPasswordAuth();

    boolean isDeviceAuth();

    default String clientIp() {
        return null;
    }
}
