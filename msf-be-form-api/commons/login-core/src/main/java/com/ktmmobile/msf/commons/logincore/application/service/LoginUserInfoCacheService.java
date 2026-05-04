package com.ktmmobile.msf.commons.logincore.application.service;

import java.util.Optional;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.common.service.CacheStampedeGuard;
import com.ktmmobile.msf.commons.common.service.dto.CacheStampedeProtection;
import com.ktmmobile.msf.commons.common.service.port.CacheService;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;
import com.ktmmobile.msf.commons.logincore.support.properties.LoginCoreProperties;

@RequiredArgsConstructor
@Service
public class LoginUserInfoCacheService {

    private final LoginCoreProperties properties;
    private final CacheService<LoginUserInfo> cacheService;
    private final CacheStampedeGuard cacheStampedeGuard;

    public void save(LoginSessionUser principal) {
        save(new LoginUserInfo(
            principal.userId(),
            principal.userName(),
            principal.phoneNumber(),
            principal.userType(),
            principal.attributes()
        ));
    }

    public void save(LoginUserInfo userInfo) {
        LoginCoreProperties.UserInfoCache cache = properties.userInfoCache();
        cacheService.setValue(key(userInfo.userType(), userInfo.userId()), userInfo, cache.timeToLive());
        cacheService.setValue(staleKey(userInfo.userType(), userInfo.userId()), userInfo, cache.timeToLive().plus(cache.staleTimeToLive()));
    }

    public Optional<LoginUserInfo> get(UserType userType, String userId) {
        return Optional.ofNullable(cacheService.getValue(key(userType, userId)));
    }

    public LoginUserInfo getOrLoad(UserType userType, String userId, Supplier<LoginUserInfo> loader) {
        return cacheStampedeGuard.getOrLoad(
            cacheService,
            protection(userType, userId),
            loader,
            () -> new LoginException("사용자 정보 갱신 중입니다. 잠시 후 다시 시도해 주세요.")
        );
    }

    public void delete(UserType userType, String userId) {
        cacheService.delete(key(userType, userId));
        cacheService.delete(staleKey(userType, userId));
    }

    private CacheStampedeProtection protection(UserType userType, String userId) {
        LoginCoreProperties.UserInfoCache cache = properties.userInfoCache();
        return new CacheStampedeProtection(
            key(userType, userId),
            staleKey(userType, userId),
            lockKey(userType, userId),
            cache.timeToLive(),
            cache.staleTimeToLive(),
            cache.lockTimeToLive(),
            cache.lockWaitTime(),
            cache.lockRetryInterval()
        );
    }

    private String key(UserType userType, String userId) {
        return "user-info:" + userType.getCode() + ":" + userId;
    }

    private String staleKey(UserType userType, String userId) {
        return key(userType, userId) + "-stale";
    }

    private String lockKey(UserType userType, String userId) {
        return "lock:user-info:" + userType.getCode() + ":" + userId;
    }
}
