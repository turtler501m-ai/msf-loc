package com.ktmmobile.msf.commons.logincore.domain.policy.requiredaction;

import java.util.Optional;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginRequiredAction;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginCompletionContext;

public interface LoginRequiredActionPolicy {

    boolean supports(LoginCompletionContext<?> context);

    Optional<LoginRequiredAction> resolve(LoginCompletionContext<?> context);
}
