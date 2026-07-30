package com.ktmmobile.msf.commons.logincore.domain.policy.requiredaction;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginRequiredAction;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginCompletionContext;

@Component
public class SmsLoginTwoFactorPolicy implements LoginRequiredActionPolicy {

    public static final String BEAN_NAME = "smsLoginTwoFactorPolicy";

    /**
     * SMS 2FA 정책 적용 가능 여부 확인
     *
     * @param context 로그인 완료 컨텍스트
     * @return 적용 가능 여부
     */
    @Override
    public boolean supports(LoginCompletionContext<?> context) {
        return context.credential() instanceof LoginAuthenticationCredential;
    }

    /**
     * SMS 2FA 필요 조치 결정
     *
     * @param context 로그인 완료 컨텍스트
     * @return 필요 조치
     */
    @Override
    public Optional<LoginRequiredAction> resolve(LoginCompletionContext<?> context) {
        return Optional.of(LoginRequiredAction.verifyTwoFactor());
    }
}
