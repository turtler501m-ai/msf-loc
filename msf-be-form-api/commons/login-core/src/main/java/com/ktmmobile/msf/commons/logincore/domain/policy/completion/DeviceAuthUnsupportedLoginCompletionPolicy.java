package com.ktmmobile.msf.commons.logincore.domain.policy.completion;

import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;

@Component(DeviceAuthUnsupportedLoginCompletionPolicy.BEAN_NAME)
public class DeviceAuthUnsupportedLoginCompletionPolicy implements LoginCompletionPolicy {

    public static final String BEAN_NAME = "deviceAuthUnsupportedLoginCompletionPolicy";

    @Override
    public boolean supports(LoginCompletionContext<?> context) {
        return true;
    }

    @Override
    public void verify(LoginCompletionContext<?> context) {
        if (context.credential().isDeviceAuth()) {
            throw new LoginException("지원하지 않는 인증방식입니다.");
        }
    }
}
