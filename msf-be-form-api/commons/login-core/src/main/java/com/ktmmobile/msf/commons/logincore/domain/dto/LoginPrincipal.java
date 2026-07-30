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

    /**
     * 로그인 세션 사용자 기준 Principal 생성
     *
     * @param user 로그인 세션 사용자
     * @return 로그인 Principal
     */
    public static LoginPrincipal from(LoginSessionUser user) {
        return new LoginPrincipal(user.userId(), user.userType(), user.organization());
    }

    /**
     * 대리점 코드 조회
     *
     * @return 대리점 코드
     */
    public String agentCode() {
        return organization.agentCode();
    }

    /**
     * 판매점 코드 조회
     *
     * @return 판매점 코드
     */
    public String shopCode() {
        return organization.shopCode();
    }
}
