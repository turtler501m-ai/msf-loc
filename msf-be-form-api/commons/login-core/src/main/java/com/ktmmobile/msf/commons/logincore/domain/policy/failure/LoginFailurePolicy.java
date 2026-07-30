package com.ktmmobile.msf.commons.logincore.domain.policy.failure;

/**
 * 로그인 실패 후 계정 잠금 등 실패 처리 여부를 판단하는 정책
 */
public interface LoginFailurePolicy {

    /**
     * 정책 적용 대상 여부 확인
     *
     * @param context 로그인 실패 정책 컨텍스트
     * @return 적용 대상 여부
     */
    boolean supports(LoginFailureContext<?> context);

    /**
     * 계정 잠금 필요 여부 판단
     *
     * @param context 로그인 실패 정책 컨텍스트
     * @return 계정 잠금 필요 여부
     */
    boolean shouldLock(LoginFailureContext<?> context);
}
