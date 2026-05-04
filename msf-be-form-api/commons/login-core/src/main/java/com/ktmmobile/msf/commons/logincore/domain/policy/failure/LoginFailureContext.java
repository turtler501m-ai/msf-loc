package com.ktmmobile.msf.commons.logincore.domain.policy.failure;

import com.ktmmobile.msf.commons.logincore.domain.entity.LoginUser;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;

public record LoginFailureContext<C extends LoginAuthenticationCredential>(
    LoginUser user,
    C credential
) {
}
