package com.ktmmobile.msf.commons.logincore.domain.policy.completion;

import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;

@Component(DeviceAuthUnsupportedLoginCompletionPolicy.BEAN_NAME)
public class DeviceAuthUnsupportedLoginCompletionPolicy implements LoginCompletionPolicy {

    public static final String BEAN_NAME = "deviceAuthUnsupportedLoginCompletionPolicy";

    /**
     * 단말 인증 미지원 정책 적용 가능 여부 확인
     *
     * @param context 로그인 완료 컨텍스트
     * @return 적용 가능 여부
     */
    @Override
    public boolean supports(LoginCompletionContext<?> context) {
        return true;
    }

    /**
     * 단말 인증 미지원 여부 검증
     *
     * @param context 로그인 완료 컨텍스트
     */
    @Override
    public void verify(LoginCompletionContext<?> context) {
        if (context.credential().isDeviceAuth()) {
            throw new LoginException("지원하지 않는 인증방식입니다.");
        }
    }
}
