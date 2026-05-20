package com.ktmmobile.msf.domains.login.application.dto;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginOrganization;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginResultUserInfo;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.masking.domain.code.MaskingType;
import com.ktmmobile.msf.commons.masking.support.annotation.Masked;

public record LoginUserInfoResponse(
    String userId,
    @Masked(type = MaskingType.NAME) String userName,
    @Masked(type = MaskingType.MOBILE_PHONE) String phoneNumber,
    String clientIp,
    boolean deviceAuthCompleted,
    Organization organization
) {

    public static LoginUserInfoResponse from(LoginUserInfo userInfo) {
        return new LoginUserInfoResponse(
            userInfo.userId(),
            userInfo.userName(),
            userInfo.phoneNumber(),
            userInfo.clientIp(),
            Boolean.TRUE.equals(userInfo.attributeAsBoolean("deviceAuthCompleted")),
            Organization.from(userInfo.organization())
        );
    }

    public static LoginUserInfoResponse from(LoginResultUserInfo userInfo) {
        return new LoginUserInfoResponse(
            userInfo.userId(),
            userInfo.userName(),
            userInfo.phoneNumber(),
            userInfo.clientIp(),
            Boolean.TRUE.equals(userInfo.attributeAsBoolean("deviceAuthCompleted")),
            Organization.from(userInfo.organization())
        );
    }

    public record Organization(
        String agentCode,
        String agentName,
        String shopCode,
        String shopName
    ) {

        public static Organization from(LoginOrganization organization) {
            return new Organization(
                organization.agentCode(),
                organization.agentName(),
                organization.shopCode(),
                organization.shopName()
            );
        }
    }
}
