package com.ktmmobile.msf.commons.logincore.domain.policy.completion;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;

@Component(DeviceAuthLoginCompletionPolicy.BEAN_NAME)
public class DeviceAuthLoginCompletionPolicy implements LoginCompletionPolicy {

    public static final String BEAN_NAME = "deviceAuthLoginCompletionPolicy";

    /**
     * 단말 인증 정책 적용 가능 여부 확인
     *
     * @param context 로그인 완료 컨텍스트
     * @return 적용 가능 여부
     */
    @Override
    public boolean supports(LoginCompletionContext<?> context) {
        return context.credential().isDeviceAuth();
    }

    /**
     * 단말 인증 요청 필수 값 검증
     *
     * @param context 로그인 완료 컨텍스트
     */
    @Override
    public void verify(LoginCompletionContext<?> context) {
        if (!StringUtils.hasText(context.credential().deviceUuid())) {
            throw new LoginException("deviceUuid는 필수 입력 값입니다.");
        }
    }
}
