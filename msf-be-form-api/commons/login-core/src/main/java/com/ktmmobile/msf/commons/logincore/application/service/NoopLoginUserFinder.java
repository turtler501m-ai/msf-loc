package com.ktmmobile.msf.commons.logincore.application.service;

import java.util.Optional;

import com.ktmmobile.msf.commons.logincore.application.port.out.LoginUserFinder;
import com.ktmmobile.msf.commons.logincore.domain.entity.LoginUser;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;

public class NoopLoginUserFinder implements LoginUserFinder<LoginAuthenticationCredential> {

    @Override
    public Optional<LoginUser> findByCredential(LoginAuthenticationCredential credential) {
        return Optional.empty();
    }

    @Override
    public void recordLoginSuccess(LoginUser user, LoginAuthenticationCredential credential) {
    }

    @Override
    public void recordLoginFailure(LoginUser user, LoginAuthenticationCredential credential, boolean shouldLock) {
    }
}
