package com.ktmmobile.msf.commons.logincore.application.service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.common.data.entity.user.AdminUser;
import com.ktmmobile.msf.commons.common.data.entity.user.FormUser;
import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;
import com.ktmmobile.msf.commons.common.data.entity.user.UserOrganization;
import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.websecurity.security.auth.port.AuthenticatedUserFinder;

@RequiredArgsConstructor
@Component
public class LoginAuthenticatedUserFinder implements AuthenticatedUserFinder {

    private static final String ROLE_CODE_ATTRIBUTE = "roleCode";

    private final LoginUserInfoResolver loginUserInfoResolver;

    /**
     * 인증 사용자 정보 조회
     *
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     * @return 인증 사용자
     */
    @Override
    public Optional<MsfUser> findUser(UserType userType, String userId) {
        return loginUserInfoResolver.resolve(userType, userId)
            .flatMap(this::toUser);
    }

    /**
     * 로그인 사용자 정보 기준 인증 사용자 변환
     *
     * @param userInfo 로그인 사용자 정보
     * @return 인증 사용자
     */
    private Optional<MsfUser> toUser(LoginUserInfo userInfo) {
        UserOrganization organization = new UserOrganization(
            userInfo.organization().agentCode(),
            userInfo.organization().agentName(),
            userInfo.organization().shopCode(),
            userInfo.organization().shopName(),
            userInfo.organization().levelCode()
        );
        if (userInfo.userType().isFormUser()) {
            return Optional.of(new FormUser(userInfo.userType(), userInfo.userId(), userInfo.userName(), organization));
        }
        if (userInfo.userType().isAdminUser()) {
            return Optional.of(new AdminUser(
                userInfo.userType(),
                userInfo.userId(),
                userInfo.userName(),
                organization,
                userInfo.attributeAsString(ROLE_CODE_ATTRIBUTE)
            ));
        }
        return Optional.empty();
    }
}
