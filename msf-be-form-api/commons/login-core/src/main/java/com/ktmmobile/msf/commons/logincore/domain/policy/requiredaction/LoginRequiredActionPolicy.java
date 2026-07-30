package com.ktmmobile.msf.commons.logincore.domain.policy.requiredaction;

import java.util.Optional;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginRequiredAction;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginCompletionContext;

/**
 * 로그인 완료 전에 필요한 후속 조치를 산출하는 정책
 */
public interface LoginRequiredActionPolicy {

    /**
     * 정책 적용 대상 여부 확인
     *
     * @param context 로그인 완료 정책 컨텍스트
     * @return 적용 대상 여부
     */
    boolean supports(LoginCompletionContext<?> context);

    /**
     * 후속 조치 산출
     *
     * @param context 로그인 완료 정책 컨텍스트
     * @return 필요한 후속 조치
     */
    Optional<LoginRequiredAction> resolve(LoginCompletionContext<?> context);
}
