package com.ktmmobile.msf.commons.logincore.domain.policy.completion;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;

@Component(PasswordLoginCompletionPolicy.BEAN_NAME)
public class PasswordLoginCompletionPolicy implements LoginCompletionPolicy {

    public static final String BEAN_NAME = "passwordLoginCompletionPolicy";

    @Override
    public boolean supports(LoginCompletionContext<?> context) {
        return context.credential().isPasswordAuth();
    }

    @Override
    public void verify(LoginCompletionContext<?> context) {
        if (!StringUtils.hasText(context.credential().password())) {
            throw new LoginException("비밀번호는 필수 입력 값입니다.");
        }
    }
}
