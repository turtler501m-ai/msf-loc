package com.ktmmobile.msf.commons.logincore.application.port.in;

import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;

public interface LoginFlowProcessor<C extends LoginAuthenticationCredential>
    extends LoginAuthenticationFlowProcessor<C>, LoginSessionFlowProcessor {
}
