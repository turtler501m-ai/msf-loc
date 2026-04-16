package com.ktmmobile.msf.domains.login.adapter.memory;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.domains.login.application.port.out.AccessTokenStore;
import com.ktmmobile.msf.domains.login.application.port.out.RefreshTokenStore;
import com.ktmmobile.msf.commons.websecurity.security.auth.port.ActiveAccessTokenPort;

@Component
@ConditionalOnProperty(prefix = "spring.data.redis", name = "enabled", havingValue = "false")
public class InMemoryLoginTokenStoreAdapter implements AccessTokenStore, RefreshTokenStore, ActiveAccessTokenPort {

    private final Map<String, ExpiringValue<String>> accessTokens = new ConcurrentHashMap<>();
    private final Map<String, ExpiringValue<String>> refreshTokens = new ConcurrentHashMap<>();

    @Override
    public void saveAccessToken(String jti, String userId, Duration timeToLive) {
        accessTokens.put(jti, ExpiringValue.of(userId, timeToLive));
    }

    @Override
    public boolean existsAccessToken(String jti) {
        return getValidValue(accessTokens, jti) != null;
    }

    @Override
    public void deleteAccessToken(String jti) {
        accessTokens.remove(jti);
    }

    @Override
    public void saveRefreshToken(String userId, String refreshToken, Duration timeToLive) {
        refreshTokens.put(userId, ExpiringValue.of(refreshToken, timeToLive));
    }

    @Override
    public String getRefreshToken(String userId) {
        return getValidValue(refreshTokens, userId);
    }

    @Override
    public void deleteRefreshToken(String userId) {
        refreshTokens.remove(userId);
    }

    @Override
    public boolean exists(String jti) {
        return existsAccessToken(jti);
    }

    private static String getValidValue(Map<String, ExpiringValue<String>> store, String key) {
        ExpiringValue<String> value = store.get(key);
        if (value == null) {
            return null;
        }
        if (value.isExpired()) {
            store.remove(key);
            return null;
        }
        return value.value();
    }

    private record ExpiringValue<T>(T value, Instant expiresAt) {

        static <T> ExpiringValue<T> of(T value, Duration ttl) {
            return new ExpiringValue<>(value, Instant.now().plus(ttl));
        }

        boolean isExpired() {
            return expiresAt.isBefore(Instant.now());
        }
    }
}
