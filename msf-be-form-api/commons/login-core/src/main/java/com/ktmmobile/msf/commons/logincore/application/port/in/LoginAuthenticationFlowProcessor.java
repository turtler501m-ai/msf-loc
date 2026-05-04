package com.ktmmobile.msf.commons.logincore.application.port.in;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginResult;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;

public interface LoginAuthenticationFlowProcessor<C extends LoginAuthenticationCredential> {

    LoginResult loginWithIdPw(C credential);
}
