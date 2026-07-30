package com.ktmmobile.msf.domains.login.domain.policy;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginAttributes;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginRequiredAction;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginCompletionContext;
import com.ktmmobile.msf.commons.logincore.domain.policy.requiredaction.LoginRequiredActionPolicy;
import com.ktmmobile.msf.domains.login.domain.code.LoginUserInfoAttribute;

@Component
public class FormDeviceAuthRequiredActionPolicy implements LoginRequiredActionPolicy {

    public static final String BEAN_NAME = "formDeviceAuthRequiredActionPolicy";

    /**
     * Form 사용자 단말 인증 정책 적용 가능 여부 확인
     *
     * @param context 로그인 완료 컨텍스트
     * @return 적용 가능 여부
     */
    @Override
    public boolean supports(LoginCompletionContext<?> context) {
        return context.credential() instanceof LoginAuthenticationCredential credential
            && credential.userType().isFormUser();
    }

    /**
     * Form 단말 인증 필요 조치 결정
     *
     * @param context 로그인 완료 컨텍스트
     * @return 필요 조치
     */
    @Override
    public Optional<LoginRequiredAction> resolve(LoginCompletionContext<?> context) {
        LoginUserInfo userInfo = context.userInfo();
        if (userInfo != null && !Boolean.TRUE.equals(LoginAttributes.getBoolean(userInfo.attributes(), LoginUserInfoAttribute.DEVICE_AUTH_COMPLETED.key()))) {
            return Optional.of(LoginRequiredAction.deviceAuth());
        }
        return Optional.empty();
    }
}
