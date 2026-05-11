package com.ktmmobile.msf.commons.logincore.domain.dto;

import com.ktmmobile.msf.commons.common.data.type.UserType;

public record LoginPrincipal(
    String userId,
    UserType userType,
    LoginOrganization organization
) {

    public LoginPrincipal {
        organization = organization == null ? LoginOrganization.empty() : organization;
    }

    public static LoginPrincipal from(LoginSessionUser user) {
        return new LoginPrincipal(user.userId(), user.userType(), user.organization());
    }

    public String agentCode() {
        return organization.agentCode();
    }

    public String shopCode() {
        return organization.shopCode();
    }
}
