package com.ktmmobile.msf.commons.logincore.domain.policy.failure;

public interface LoginFailurePolicy {

    boolean supports(LoginFailureContext<?> context);

    boolean shouldLock(LoginFailureContext<?> context);
}
