package com.ktmmobile.msf.commons.logincore.application.port.in;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginResult;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;

/**
 * ID/PW 기반 로그인 시작 포트
 *
 * @param <C> 앱별 로그인 credential 타입
 */
public interface LoginAuthenticationFlowProcessor<C extends LoginAuthenticationCredential> {

    /**
     * ID/PW 로그인 수행
     *
     * @param credential 로그인 credential
     * @return 로그인 진행 결과
     */
    LoginResult loginWithIdPw(C credential);

    /**
     * 생체인증 로그인 수행
     *
     * @param credential 로그인 credential
     * @return 로그인 진행 결과
     */
    LoginResult loginWithBiometric(C credential);
}
