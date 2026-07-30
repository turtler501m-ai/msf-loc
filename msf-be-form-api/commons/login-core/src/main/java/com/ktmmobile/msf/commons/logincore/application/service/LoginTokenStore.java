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

/**
 * Redis 기반 토큰 JTI whitelist 저장소
 */
@RequiredArgsConstructor
@Service
public class LoginTokenStore implements ActiveTokenChecker {

    private final CacheService<String> tokenCacheService;

    /**
     * 토큰 JTI 저장
     *
     * @param tokenType 토큰 유형
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     * @param jti JWT ID
     * @param timeToLive 만료 시간
     */
    public void saveTokenJti(TokenType tokenType, UserType userType, String userId, String jti, Duration timeToLive) {
        tokenCacheService.setValue(tokenKey(tokenType, userType, userId), jti, timeToLive);
    }

    /**
     * 활성 토큰 여부 확인
     *
     * @param tokenType 토큰 유형
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     * @param jti JWT ID
     * @return 활성 토큰 여부
     */
    @Override
    public boolean exists(TokenType tokenType, UserType userType, String userId, String jti) {
        if (!valid(userType, userId, jti)) {
            return false;
        }
        return Objects.equals(getActiveTokenJti(tokenType, userType, userId), jti);
    }

    /**
     * 현재 활성 토큰 JTI 조회
     *
     * @param tokenType 토큰 유형
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     * @return 현재 활성 토큰 JTI
     */
    @Override
    public String getActiveTokenJti(TokenType tokenType, UserType userType, String userId) {
        if (userType == null || !userType.isValid() || !StringUtils.hasText(userId)) {
            return null;
        }
        return tokenCacheService.getValue(tokenKey(tokenType, userType, userId));
    }

    /**
     * 사용자 토큰 삭제
     *
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     */
    public void deleteTokens(UserType userType, String userId) {
        if (userType == null || !userType.isValid() || !StringUtils.hasText(userId)) {
            return;
        }
        tokenCacheService.delete(tokenKey(TokenType.ACCESS, userType, userId));
        tokenCacheService.delete(tokenKey(TokenType.REFRESH, userType, userId));
    }

    /**
     * 토큰 조회 필수 값 유효 여부 확인
     *
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     * @param jti JWT ID
     * @return 유효 여부
     */
    private boolean valid(UserType userType, String userId, String jti) {
        return userType != null && userType.isValid() && StringUtils.hasText(userId) && StringUtils.hasText(jti);
    }

    /**
     * 토큰 캐시 키 생성
     *
     * @param tokenType 토큰 유형
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     * @return 토큰 캐시 키
     */
    private String tokenKey(TokenType tokenType, UserType userType, String userId) {
        // 사용자 유형별 ID 충돌 방지를 위해 userType을 키에 포함
        return "token:" + userType.getCode() + ":" + userId + ":" + tokenType.getClaimValue();
    }
}
