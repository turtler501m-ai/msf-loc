package com.ktmmobile.msf.commons.logincore.application.service;

import java.time.Duration;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.common.service.port.CacheService;
import com.ktmmobile.msf.commons.websecurity.security.auth.port.ActiveAccessTokenPort;

@RequiredArgsConstructor
@Service
public class LoginTokenStore implements ActiveAccessTokenPort {

    private final CacheService<String> cacheService;

    public void saveAccessTokenJti(UserType userType, String userId, String jti, Duration timeToLive) {
        cacheService.setValue(accessTokenKey(userType, userId), jti, timeToLive);
    }

    public void saveRefreshTokenJti(UserType userType, String userId, String jti, Duration timeToLive) {
        cacheService.setValue(refreshTokenKey(userType, userId), jti, timeToLive);
    }

    @Override
    public boolean exists(UserType userType, String userId, String jti) {
        if (!valid(userType, userId, jti)) {
            return false;
        }
        return Objects.equals(cacheService.getValue(accessTokenKey(userType, userId)), jti);
    }

    public boolean matchesRefreshToken(UserType userType, String userId, String jti) {
        if (!valid(userType, userId, jti)) {
            return false;
        }
        return Objects.equals(cacheService.getValue(refreshTokenKey(userType, userId)), jti);
    }

    public void deleteTokens(UserType userType, String userId) {
        if (userType == null || !userType.isValid() || !StringUtils.hasText(userId)) {
            return;
        }
        cacheService.delete(accessTokenKey(userType, userId));
        cacheService.delete(refreshTokenKey(userType, userId));
    }

    private boolean valid(UserType userType, String userId, String jti) {
        return userType != null && userType.isValid() && StringUtils.hasText(userId) && StringUtils.hasText(jti);
    }

    private String accessTokenKey(UserType userType, String userId) {
        return tokenKey(userType, userId, "access");
    }

    private String refreshTokenKey(UserType userType, String userId) {
        return tokenKey(userType, userId, "refresh");
    }

    private String tokenKey(UserType userType, String userId, String tokenType) {
        return "token:" + userType.getCode() + ":" + userId + ":" + tokenType;
    }
}
