package com.ktmmobile.msf.commons.logincore.application.port.out;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.entity.LoginUser;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;

/**
 * 앱별 로그인 인증 성공/실패 이력 갱신을 담당하는 아웃바운드 포트
 *
 * @param <C> 앱별 로그인 credential 타입
 */
public interface LoginAuthenticationRecorder<C extends LoginAuthenticationCredential> {

    /**
     * Access Token 발급 직후 로그인 성공 이력 갱신
     *
     * @param user 로그인 세션 사용자
     */
    void recordAccessTokenIssueSuccess(LoginSessionUser user);

    /**
     * 인증 대상 사용자 조회 실패 이력 갱신
     *
     * @param credential 로그인 credential
     */
    default void recordLoginFailure(C credential) {
        // Noop
    }

    /**
     * 로그인 실패 이력 갱신
     *
     * @param user 인증 대상 사용자
     * @param credential 로그인 credential
     * @param shouldLock 계정 잠금 처리 여부
     */
    void recordLoginFailure(LoginUser user, C credential, boolean shouldLock);
}
