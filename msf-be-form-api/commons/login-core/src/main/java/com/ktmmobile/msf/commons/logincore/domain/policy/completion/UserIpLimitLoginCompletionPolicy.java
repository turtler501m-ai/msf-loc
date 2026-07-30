package com.ktmmobile.msf.commons.logincore.domain.policy.completion;

import java.util.Arrays;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;

@Component(UserIpLimitLoginCompletionPolicy.BEAN_NAME)
public class UserIpLimitLoginCompletionPolicy implements LoginCompletionPolicy {

    public static final String BEAN_NAME = "userIpLimitLoginCompletionPolicy";

    /**
     * 클라이언트 IP 허용 정책 적용 가능 여부 확인
     *
     * @param context 로그인 완료 컨텍스트
     * @return 적용 가능 여부
     */
    @Override
    public boolean supports(LoginCompletionContext<?> context) {
        return true;
    }

    /**
     * 클라이언트 IP 허용 여부 검증
     *
     * @param context 로그인 완료 컨텍스트
     */
    @Override
    public void verify(LoginCompletionContext<?> context) {
        String clientIp = context.credential().clientIp();
        if (RequestUtils.LOCALHOST_IPV4.equals(clientIp)) {
            return;
        }

        String allowedClientIps = context.user().allowedClientIps();
        if (!StringUtils.hasText(allowedClientIps)) {
            return;
        }
        boolean allowed = Arrays.stream(allowedClientIps.split(","))
            .map(String::trim)
            .filter(StringUtils::hasText)
            .anyMatch(allowedIp -> allowedIp.equals(clientIp));
        if (!allowed) {
            throw new LoginException("허용되지 않은 접속 IP입니다.");
        }
    }
}
