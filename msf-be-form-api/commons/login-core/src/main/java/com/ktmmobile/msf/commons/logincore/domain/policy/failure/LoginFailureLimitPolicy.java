package com.ktmmobile.msf.commons.logincore.domain.policy.failure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.logincore.support.properties.LoginCoreProperties;

@RequiredArgsConstructor
@Component(LoginFailureLimitPolicy.BEAN_NAME)
public class LoginFailureLimitPolicy implements LoginFailurePolicy {

    public static final String BEAN_NAME = "loginFailureLimitPolicy";

    private final LoginCoreProperties properties;

    @Override
    public boolean supports(LoginFailureContext<?> context) {
        return true;
    }

    @Override
    public boolean shouldLock(LoginFailureContext<?> context) {
        return context.user().loginFailCount() >= properties.failure().maxCount();
    }
}
