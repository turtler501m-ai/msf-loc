package com.ktmmobile.msf.domains.login.application.dto;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginResultUserInfo;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginAttributes;
import com.ktmmobile.msf.domains.login.domain.code.LoginUserInfoAttribute;

public record LoginUserInfoResponse(
    String userId,
    String userName,
    String phoneNumber,
    boolean deviceAuthCompleted,
    String agentCode,
    String shopCode
) {

    public static LoginUserInfoResponse from(LoginUserInfo userInfo) {
        return new LoginUserInfoResponse(
            userInfo.userId(),
            userInfo.userName(),
            userInfo.phoneNumber(),
            Boolean.TRUE.equals(LoginAttributes.getBoolean(userInfo.attributes(), LoginUserInfoAttribute.DEVICE_AUTH_COMPLETED.key())),
            LoginAttributes.getString(userInfo.attributes(), LoginUserInfoAttribute.AGENT_CODE.key()),
            LoginAttributes.getString(userInfo.attributes(), LoginUserInfoAttribute.SHOP_CODE.key())
        );
    }

    public static LoginUserInfoResponse from(LoginResultUserInfo userInfo) {
        return new LoginUserInfoResponse(
            userInfo.userId(),
            userInfo.userName(),
            userInfo.phoneNumber(),
            Boolean.TRUE.equals(LoginAttributes.getBoolean(userInfo.attributes(), LoginUserInfoAttribute.DEVICE_AUTH_COMPLETED.key())),
            LoginAttributes.getString(userInfo.attributes(), LoginUserInfoAttribute.AGENT_CODE.key()),
            LoginAttributes.getString(userInfo.attributes(), LoginUserInfoAttribute.SHOP_CODE.key())
        );
    }
}
