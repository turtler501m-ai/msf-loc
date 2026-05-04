package com.ktmmobile.msf.commons.logincore.domain.policy.completion;

import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;

@Component(UserStatusLoginCompletionPolicy.BEAN_NAME)
public class UserStatusLoginCompletionPolicy implements LoginCompletionPolicy {

    public static final String BEAN_NAME = "userStatusLoginCompletionPolicy";

    @Override
    public boolean supports(LoginCompletionContext<?> context) {
        return true;
    }

    @Override
    public void verify(LoginCompletionContext<?> context) {
        if (!context.user().enabled()) {
            throw new LoginException("로그인에 실패했습니다. 관리자에게 문의하세요.");
        }
    }
}
