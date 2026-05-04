package com.ktmmobile.msf.commons.logincore.application.port.out;

import java.util.Optional;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.logincore.domain.entity.LoginUser;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;

public interface LoginUserFinder<C extends LoginAuthenticationCredential> {

    Optional<LoginUser> findByCredential(C credential);

    default Optional<LoginUserInfo> findUserInfo(LoginUser user, C credential) {
        return Optional.of(LoginUserInfo.of(user, credential.userType()));
    }

    void recordLoginSuccess(LoginUser user, C credential);

    void recordLoginFailure(LoginUser user, C credential, boolean shouldLock);
}
