package com.ktmmobile.msf.commons.logincore.application.port.in;

import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;

/**
 * 로그인 시작과 로그인 세션 후속 처리를 함께 제공하는 통합 인바운드 포트
 *
 * @param <C> 앱별 로그인 credential 타입
 */
public interface LoginFlowProcessor<C extends LoginAuthenticationCredential>
    extends LoginAuthenticationFlowProcessor<C>, LoginSessionFlowProcessor {
}
