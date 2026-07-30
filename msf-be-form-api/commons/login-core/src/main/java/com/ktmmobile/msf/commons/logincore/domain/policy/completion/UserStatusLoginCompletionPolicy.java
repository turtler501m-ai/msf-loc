package com.ktmmobile.msf.commons.logincore.domain.policy.completion;

import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;

@Component(UserStatusLoginCompletionPolicy.BEAN_NAME)
public class UserStatusLoginCompletionPolicy implements LoginCompletionPolicy {

    public static final String BEAN_NAME = "userStatusLoginCompletionPolicy";

    /**
     * 사용자 상태 정책 적용 가능 여부 확인
     *
     * @param context 로그인 완료 컨텍스트
     * @return 적용 가능 여부
     */
    @Override
    public boolean supports(LoginCompletionContext<?> context) {
        return true;
    }

    /**
     * 사용자 활성 상태 검증
     *
     * @param context 로그인 완료 컨텍스트
     */
    @Override
    public void verify(LoginCompletionContext<?> context) {
        if (!context.user().enabled()) {
            throw new LoginException("로그인에 실패했습니다. 관리자에게 문의하세요.");
        }
    }
}
