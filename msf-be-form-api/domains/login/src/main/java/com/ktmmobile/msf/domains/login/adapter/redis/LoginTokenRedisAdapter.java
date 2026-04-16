package com.ktmmobile.msf.domains.login.adapter.redis;

import java.time.Duration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.domains.login.application.port.out.AccessTokenStore;
import com.ktmmobile.msf.domains.login.application.port.out.RefreshTokenStore;
import com.ktmmobile.msf.commons.websecurity.security.auth.port.ActiveAccessTokenPort;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring.data.redis", name = "enabled", havingValue = "true", matchIfMissing = true)
public class LoginTokenRedisAdapter implements AccessTokenStore, RefreshTokenStore, ActiveAccessTokenPort {

    private static final String ACCESS_TOKEN_PREFIX = "AccessToken:";
    private static final String REFRESH_TOKEN_PREFIX = "RefreshToken:";

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveAccessToken(String jti, String userId, Duration timeToLive) {
        redisTemplate.opsForValue().set(ACCESS_TOKEN_PREFIX + jti, userId, timeToLive);
    }

    @Override
    public boolean existsAccessToken(String jti) {
        Boolean hasKey = redisTemplate.hasKey(ACCESS_TOKEN_PREFIX + jti);
        return Boolean.TRUE.equals(hasKey);
    }

    @Override
    public void deleteAccessToken(String jti) {
        redisTemplate.delete(ACCESS_TOKEN_PREFIX + jti);
    }

    @Override
    public void saveRefreshToken(String userId, String refreshToken, Duration timeToLive) {
        redisTemplate.opsForValue().set(REFRESH_TOKEN_PREFIX + userId, refreshToken, timeToLive);
    }

    @Override
    public String getRefreshToken(String userId) {
        Object refreshToken = redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + userId);
        return refreshToken == null ? null : refreshToken.toString();
    }

    @Override
    public void deleteRefreshToken(String userId) {
        redisTemplate.delete(REFRESH_TOKEN_PREFIX + userId);
    }

    @Override
    public boolean exists(String jti) {
        return existsAccessToken(jti);
    }
}
