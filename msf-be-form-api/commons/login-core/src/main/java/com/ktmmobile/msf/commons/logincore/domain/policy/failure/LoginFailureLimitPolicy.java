package com.ktmmobile.msf.commons.logincore.domain.policy.failure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.logincore.support.properties.LoginCoreProperties;

@RequiredArgsConstructor
@Component(LoginFailureLimitPolicy.BEAN_NAME)
public class LoginFailureLimitPolicy implements LoginFailurePolicy {

    public static final String BEAN_NAME = "loginFailureLimitPolicy";

    private final LoginCoreProperties properties;

    /**
     * 로그인 실패 제한 정책 적용 가능 여부 확인
     *
     * @param context 로그인 실패 컨텍스트
     * @return 적용 가능 여부
     */
    @Override
    public boolean supports(LoginFailureContext<?> context) {
        return true;
    }

    /**
     * 계정 잠금 필요 여부 확인
     *
     * @param context 로그인 실패 컨텍스트
     * @return 계정 잠금 필요 여부
     */
    @Override
    public boolean shouldLock(LoginFailureContext<?> context) {
        return context.user().loginFailCount() >= properties.failure().maxCount();
    }
}
