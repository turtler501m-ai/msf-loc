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

    private final LoginUserInfoResolver loginUserInfoResolver;

    @Override
    public Optional<MsfUser> findUser(UserType userType, String userId) {
        return loginUserInfoResolver.resolve(userType, userId)
            .flatMap(this::toUser);
    }

    private Optional<MsfUser> toUser(LoginUserInfo userInfo) {
        UserOrganization organization = new UserOrganization(
            userInfo.organization().agentCode(),
            userInfo.organization().agentName(),
            userInfo.organization().shopCode(),
            userInfo.organization().shopName()
        );
        if (userInfo.userType().isFormUser()) {
            return Optional.of(new FormUser(userInfo.userType(), userInfo.userId(), userInfo.userName(), organization));
        }
        if (userInfo.userType().isAdminUser()) {
            return Optional.of(new AdminUser(userInfo.userType(), userInfo.userId(), userInfo.userName(), organization));
        }
        return Optional.empty();
    }
}
