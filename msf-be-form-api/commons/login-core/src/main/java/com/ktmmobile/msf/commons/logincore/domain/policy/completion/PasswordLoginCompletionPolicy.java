package com.ktmmobile.msf.commons.logincore.domain.policy.completion;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;

@Component(PasswordLoginCompletionPolicy.BEAN_NAME)
public class PasswordLoginCompletionPolicy implements LoginCompletionPolicy {

    public static final String BEAN_NAME = "passwordLoginCompletionPolicy";

    /**
     * 비밀번호 로그인 정책 적용 가능 여부 확인
     *
     * @param context 로그인 완료 컨텍스트
     * @return 적용 가능 여부
     */
    @Override
    public boolean supports(LoginCompletionContext<?> context) {
        return context.credential().isPasswordAuth();
    }

    /**
     * 비밀번호 입력 검증
     *
     * @param context 로그인 완료 컨텍스트
     */
    @Override
    public void verify(LoginCompletionContext<?> context) {
        if (!StringUtils.hasText(context.credential().password())) {
            throw new LoginException("비밀번호는 필수 입력 값입니다.");
        }
    }
}
