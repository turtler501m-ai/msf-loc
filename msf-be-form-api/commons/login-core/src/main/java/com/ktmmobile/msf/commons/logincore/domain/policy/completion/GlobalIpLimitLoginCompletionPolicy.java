package com.ktmmobile.msf.commons.logincore.domain.policy.completion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.logincore.adapter.repository.mybatis.smartform.mapper.LoginCoreMapper;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;

@RequiredArgsConstructor
@Component(GlobalIpLimitLoginCompletionPolicy.BEAN_NAME)
public class GlobalIpLimitLoginCompletionPolicy implements LoginCompletionPolicy {

    public static final String BEAN_NAME = "globalIpLimitLoginCompletionPolicy";

    private final LoginCoreMapper loginCoreMapper;

    /**
     * 클라이언트 IP 제한 정책 적용 가능 여부 확인
     *
     * @param context 로그인 완료 컨텍스트
     * @return 적용 가능 여부
     */
    @Override
    public boolean supports(LoginCompletionContext<?> context) {
        return StringUtils.hasText(context.credential().clientIp());
    }

    /**
     * 클라이언트 IP 제한 여부 검증
     *
     * @param context 로그인 완료 컨텍스트
     */
    @Override
    public void verify(LoginCompletionContext<?> context) {
        String userId = context.user() == null ? null : context.user().userId();
        if (loginCoreMapper.existsLimitedAccessIp(context.credential().clientIp(), userId)) {
            throw new LoginException("제한된 접속 IP입니다.");
        }
    }
}
