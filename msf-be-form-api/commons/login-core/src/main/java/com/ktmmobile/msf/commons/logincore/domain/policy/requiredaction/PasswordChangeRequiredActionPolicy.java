package com.ktmmobile.msf.commons.logincore.domain.policy.requiredaction;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginRequiredAction;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginCompletionContext;

@Component(PasswordChangeRequiredActionPolicy.BEAN_NAME)
public class PasswordChangeRequiredActionPolicy implements LoginRequiredActionPolicy {

    public static final String BEAN_NAME = "passwordChangeRequiredActionPolicy";

    /**
     * 비밀번호 변경 조치 정책 적용 가능 여부 확인
     *
     * @param context 로그인 완료 컨텍스트
     * @return 적용 가능 여부
     */
    @Override
    public boolean supports(LoginCompletionContext<?> context) {
        return true;
    }

    /**
     * 비밀번호 변경 필요 조치 결정
     *
     * @param context 로그인 완료 컨텍스트
     * @return 필요 조치
     */
    @Override
    public Optional<LoginRequiredAction> resolve(LoginCompletionContext<?> context) {
        if (context.user().passwordChangeRequired()) {
            return Optional.of(LoginRequiredAction.passwordChange());
        }
        return Optional.empty();
    }
}
