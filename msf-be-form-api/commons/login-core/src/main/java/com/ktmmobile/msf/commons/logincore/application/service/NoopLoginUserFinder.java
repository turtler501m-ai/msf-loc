package com.ktmmobile.msf.commons.logincore.application.service;

import java.util.Optional;

import com.ktmmobile.msf.commons.logincore.application.port.out.LoginUserFinder;
import com.ktmmobile.msf.commons.logincore.domain.entity.LoginUser;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;

public class NoopLoginUserFinder implements LoginUserFinder<LoginAuthenticationCredential> {

    /**
     * 기본 로그인 사용자 조회
     *
     * @param credential 로그인 인증 정보
     * @return 빈 사용자 정보
     */
    @Override
    public Optional<LoginUser> findByCredential(LoginAuthenticationCredential credential) {
        return Optional.empty();
    }

    /**
     * 기본 인증 완료 사용자 검증
     *
     * @param user 로그인 사용자
     * @param credential 로그인 인증 정보
     */
    @Override
    public void verifyAuthenticatedUser(LoginUser user, LoginAuthenticationCredential credential) {
        //noop
    }
}
