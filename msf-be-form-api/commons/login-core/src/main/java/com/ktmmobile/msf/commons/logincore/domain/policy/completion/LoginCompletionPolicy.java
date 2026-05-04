package com.ktmmobile.msf.commons.logincore.domain.policy.completion;

public interface LoginCompletionPolicy {

    boolean supports(LoginCompletionContext<?> context);

    void verify(LoginCompletionContext<?> context);
}
