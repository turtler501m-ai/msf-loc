package com.ktmmobile.msf.commons.logincore.domain.policy.requiredaction;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginRequiredAction;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginCompletionContext;

@Component(PasswordChangeRequiredActionPolicy.BEAN_NAME)
public class PasswordChangeRequiredActionPolicy implements LoginRequiredActionPolicy {

    public static final String BEAN_NAME = "passwordChangeRequiredActionPolicy";

    @Override
    public boolean supports(LoginCompletionContext<?> context) {
        return true;
    }

    @Override
    public Optional<LoginRequiredAction> resolve(LoginCompletionContext<?> context) {
        if (context.user().passwordChangeRequired()) {
            return Optional.of(LoginRequiredAction.passwordChange());
        }
        return Optional.empty();
    }
}
