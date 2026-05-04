package com.ktmmobile.msf.commons.logincore.domain.policy.completion;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.logincore.domain.entity.LoginUser;

public record LoginCompletionContext<C extends LoginCompletionCredential>(
    LoginUser user,
    C credential,
    LoginUserInfo userInfo
) {

    public LoginCompletionContext(LoginUser user, C credential) {
        this(user, credential, null);
    }
}
