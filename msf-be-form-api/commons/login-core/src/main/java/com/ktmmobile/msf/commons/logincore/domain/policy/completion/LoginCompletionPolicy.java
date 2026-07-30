package com.ktmmobile.msf.commons.logincore.domain.policy.completion;

/**
 * 비밀번호 검증 전후 로그인 완료 가능 여부를 검증하는 정책
 */
public interface LoginCompletionPolicy {

    /**
     * 정책 적용 대상 여부 확인
     *
     * @param context 로그인 완료 정책 컨텍스트
     * @return 적용 대상 여부
     */
    boolean supports(LoginCompletionContext<?> context);

    /**
     * 정책 검증
     *
     * @param context 로그인 완료 정책 컨텍스트
     */
    void verify(LoginCompletionContext<?> context);
}
