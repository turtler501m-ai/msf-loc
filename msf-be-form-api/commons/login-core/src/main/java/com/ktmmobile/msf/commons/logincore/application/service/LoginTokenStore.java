package com.ktmmobile.msf.commons.logincore.application.service;

import java.time.Duration;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.common.service.port.CacheService;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.TokenType;
import com.ktmmobile.msf.commons.websecurity.security.auth.port.ActiveTokenChecker;

@RequiredArgsConstructor
@Service
public class LoginTokenStore implements ActiveTokenChecker {

    private final CacheService<String> tokenCacheService;

    public void saveTokenJti(TokenType tokenType, UserType userType, String userId, String jti, Duration timeToLive) {
        tokenCacheService.setValue(tokenKey(tokenType, userType, userId), jti, timeToLive);
    }

    @Override
    public boolean exists(TokenType tokenType, UserType userType, String userId, String jti) {
        if (!valid(userType, userId, jti)) {
            return false;
        }
        return Objects.equals(tokenCacheService.getValue(tokenKey(tokenType, userType, userId)), jti);
    }

    public void deleteTokens(UserType userType, String userId) {
        if (userType == null || !userType.isValid() || !StringUtils.hasText(userId)) {
            return;
        }
        tokenCacheService.delete(tokenKey(TokenType.ACCESS, userType, userId));
        tokenCacheService.delete(tokenKey(TokenType.REFRESH, userType, userId));
    }

    private boolean valid(UserType userType, String userId, String jti) {
        return userType != null && userType.isValid() && StringUtils.hasText(userId) && StringUtils.hasText(jti);
    }

    private String tokenKey(TokenType tokenType, UserType userType, String userId) {
        return "token:" + userType.getCode() + ":" + userId + ":" + tokenType.getClaimValue();
    }
}
