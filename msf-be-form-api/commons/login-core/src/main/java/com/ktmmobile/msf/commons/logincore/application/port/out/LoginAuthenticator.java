package com.ktmmobile.msf.commons.logincore.application.port.out;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginResult;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;

public interface LoginAuthenticator<C extends LoginAuthenticationCredential> {

    LoginResult authenticate(C credential);
}
