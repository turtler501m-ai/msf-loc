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

    /**
     * 사용자 정보 응답 변환
     *
     * @param userInfo 사용자 정보
     * @return 사용자 정보 응답
     */
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

    /**
     * 로그인 결과 사용자 정보 응답 변환
     *
     * @param userInfo 로그인 결과 사용자 정보
     * @return 사용자 정보 응답
     */
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

        /**
         * 로그인 조직 정보 응답 변환
         *
         * @param organization 로그인 조직 정보
         * @return 조직 정보 응답
         */
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
