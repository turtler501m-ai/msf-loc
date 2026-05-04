package com.ktmmobile.msf.commons.logincore.domain.policy.completion;

import java.util.Arrays;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;

@Component(ClientIpAllowlistLoginCompletionPolicy.BEAN_NAME)
public class ClientIpAllowlistLoginCompletionPolicy implements LoginCompletionPolicy {

    public static final String BEAN_NAME = "clientIpAllowlistLoginCompletionPolicy";

    @Override
    public boolean supports(LoginCompletionContext<?> context) {
        return true;
    }

    @Override
    public void verify(LoginCompletionContext<?> context) {
        String allowedClientIps = context.user().allowedClientIps();
        if (!StringUtils.hasText(allowedClientIps)) {
            return;
        }
        String clientIp = context.credential().clientIp();
        boolean allowed = Arrays.stream(allowedClientIps.split(","))
            .map(String::trim)
            .filter(StringUtils::hasText)
            .anyMatch(allowedIp -> allowedIp.equals(clientIp));
        if (!allowed) {
            throw new LoginException("허용되지 않은 접속 IP입니다.");
        }
    }
}
