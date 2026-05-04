package com.ktmmobile.msf.commons.logincore.domain.policy.requiredaction;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginRequiredAction;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginCompletionContext;

@Component
public class SmsLoginTwoFactorPolicy implements LoginRequiredActionPolicy {

    public static final String BEAN_NAME = "smsLoginTwoFactorPolicy";

    @Override
    public boolean supports(LoginCompletionContext<?> context) {
        return context.credential() instanceof LoginAuthenticationCredential;
    }

    @Override
    public Optional<LoginRequiredAction> resolve(LoginCompletionContext<?> context) {
        return Optional.of(LoginRequiredAction.verifyTwoFactor());
    }
}
