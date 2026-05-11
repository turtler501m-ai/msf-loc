package com.ktmmobile.msf.commons.logincore.application.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.common.data.entity.user.AdminUser;
import com.ktmmobile.msf.commons.common.data.entity.user.FormUser;
import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;
import com.ktmmobile.msf.commons.common.data.entity.user.UserOrganization;
import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.logincore.application.port.out.LoginUserFinder;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.websecurity.security.auth.port.AuthenticatedUserFinder;

@RequiredArgsConstructor
@Component
public class LoginAuthenticatedUserFinder implements AuthenticatedUserFinder {

    private final LoginUserInfoCacheService loginUserInfoCacheService;
    private final LoginUserFinder<?> loginUserFinder;

    @Override
    public Optional<MsfUser> findUser(UserType userType, String userId) {
        return loginUserInfoCacheService.get(userType, userId)
            .or(() -> loadAndCache(userType, userId))
            .flatMap(this::toUser);
    }

    private Optional<LoginUserInfo> loadAndCache(UserType userType, String userId) {
        LoginSessionUser sessionUser = new LoginSessionUser(
            userId,
            userType,
            null,
            null,
            null,
            Map.of(),
            List.of()
        );
        Optional<LoginUserInfo> userInfo = loginUserFinder.findUserInfo(sessionUser);
        userInfo.ifPresent(loginUserInfoCacheService::save);
        return userInfo;
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
