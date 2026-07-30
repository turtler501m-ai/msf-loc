package com.ktmmobile.msf.commons.logincore.application.port.out;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginResult;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;

/**
 * 로그인 인증 흐름 실행 포트
 *
 * @param <C> 앱별 로그인 credential 타입
 */
public interface LoginAuthenticator<C extends LoginAuthenticationCredential> {

    /**
     * credential 기반 인증 처리
     *
     * @param credential 로그인 credential
     * @return 로그인 진행 결과
     */
    LoginResult authenticate(C credential);
}
