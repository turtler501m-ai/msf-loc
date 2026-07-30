package com.ktmmobile.msf.commons.logincore.domain.policy.completion;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.logincore.domain.entity.LoginUser;

public record LoginCompletionContext<C extends LoginCompletionCredential>(
    LoginUser user,
    C credential,
    LoginUserInfo userInfo
) {

    /**
     * 사용자 정보 없는 로그인 완료 컨텍스트 생성
     *
     * @param user 로그인 사용자
     * @param credential 로그인 인증 정보
     */
    public LoginCompletionContext(LoginUser user, C credential) {
        this(user, credential, null);
    }
}
