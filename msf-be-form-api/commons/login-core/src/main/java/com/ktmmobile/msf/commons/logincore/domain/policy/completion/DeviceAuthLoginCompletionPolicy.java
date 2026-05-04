package com.ktmmobile.msf.commons.logincore.domain.policy.completion;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;

@Component(DeviceAuthLoginCompletionPolicy.BEAN_NAME)
public class DeviceAuthLoginCompletionPolicy implements LoginCompletionPolicy {

    public static final String BEAN_NAME = "deviceAuthLoginCompletionPolicy";

    @Override
    public boolean supports(LoginCompletionContext<?> context) {
        return context.credential().isDeviceAuth();
    }

    @Override
    public void verify(LoginCompletionContext<?> context) {
        if (!StringUtils.hasText(context.credential().deviceUuid())) {
            throw new LoginException("deviceUuid는 필수 입력 값입니다.");
        }
    }
}
