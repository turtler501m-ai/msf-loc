package com.ktmmobile.msf.commons.logincore.domain.dto;

import com.ktmmobile.msf.commons.common.data.type.UserType;

public record LoginPrincipal(
    String userId,
    UserType userType
) {

    public static LoginPrincipal from(LoginSessionUser user) {
        return new LoginPrincipal(user.userId(), user.userType());
    }
}
