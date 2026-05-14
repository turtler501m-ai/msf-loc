package com.ktmmobile.msf.commons.logincore.application.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.logincore.application.port.out.LoginUserFinder;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;

@RequiredArgsConstructor
@Component
public class LoginUserInfoResolver {

    private final LoginUserInfoCacheService loginUserInfoCacheService;
    private final LoginUserFinder<?> loginUserFinder;

    public Optional<LoginUserInfo> resolve(UserType userType, String userId) {
        return resolve(sessionUser(userType, userId));
    }

    public Optional<LoginUserInfo> resolve(LoginSessionUser sessionUser) {
        return Optional.of(loginUserInfoCacheService.getOrLoad(sessionUser.userType(), sessionUser.userId(), load(sessionUser)));
    }

    private Supplier<LoginUserInfo> load(LoginSessionUser sessionUser) {
        return () -> loginUserFinder.findUserInfo(sessionUser)
            .orElseThrow(() -> new LoginException("사용자 인증 정보를 조회할 수 없습니다."));
    }

    private LoginSessionUser sessionUser(UserType userType, String userId) {
        return new LoginSessionUser(
            userId,
            userType,
            null,
            null,
            null,
            Map.of(),
            List.of()
        );
    }
}
