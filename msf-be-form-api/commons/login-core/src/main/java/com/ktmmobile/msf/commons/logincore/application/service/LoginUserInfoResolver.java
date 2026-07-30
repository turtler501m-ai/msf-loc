package com.ktmmobile.msf.commons.logincore.application.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.logincore.application.port.out.LoginUserFinder;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;

/**
 * 사용자 정보 캐시를 우선 사용하고 필요 시 앱별 저장소로 재조회하는 resolver
 */
@RequiredArgsConstructor
@Component
public class LoginUserInfoResolver {

    private final LoginUserInfoCacheService loginUserInfoCacheService;
    private final LoginUserFinder<?> loginUserFinder;

    /**
     * 사용자 유형과 ID 기준 사용자 정보 조회
     *
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     * @return 사용자 정보
     */
    public Optional<LoginUserInfo> resolve(UserType userType, String userId) {
        return resolve(sessionUser(userType, userId));
    }

    /**
     * 로그인 세션 사용자 기준 사용자 정보 조회
     *
     * @param sessionUser 로그인 세션 사용자
     * @return 사용자 정보
     */
    public Optional<LoginUserInfo> resolve(LoginSessionUser sessionUser) {
        return Optional.of(loginUserInfoCacheService.getOrLoad(sessionUser.userType(), sessionUser.userId(), load(sessionUser)))
            .filter(userInfo -> matches(sessionUser, userInfo));
    }

    /**
     * 사용자 정보 적재 함수 생성
     *
     * @param sessionUser 로그인 세션 사용자
     * @return 사용자 정보 적재 함수
     */
    private Supplier<LoginUserInfo> load(LoginSessionUser sessionUser) {
        return () -> loginUserFinder.findUserInfo(sessionUser)
            .filter(userInfo -> matches(sessionUser, userInfo))
            .orElseThrow(() -> new LoginException("사용자 인증 정보를 조회할 수 없습니다."));
    }

    private boolean matches(LoginSessionUser sessionUser, LoginUserInfo userInfo) {
        return Objects.equals(sessionUser.userId(), userInfo.userId())
            && sessionUser.userType() == userInfo.userType();
    }

    /**
     * 최소 로그인 세션 사용자 생성
     *
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     * @return 로그인 세션 사용자
     */
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
