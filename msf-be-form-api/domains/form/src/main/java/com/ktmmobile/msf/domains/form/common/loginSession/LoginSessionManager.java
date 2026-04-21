package com.ktmmobile.msf.domains.form.common.loginSession;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring.data.redis", name = "enabled", havingValue = "true", matchIfMissing = true)
public class LoginSessionManager {

    private final StringRedisTemplate redisTemplate;
    private static final String REDIS_PREFIX = "USER_SESSION:";

    public void login(String userId, String deviceId) {
        String key = REDIS_PREFIX + userId;

        // 1. 기존 로그인 정보 확인
        String existingDevice = redisTemplate.opsForValue().get(key);

        if (existingDevice != null && !existingDevice.equals(deviceId)) {
            // 중복 로그인 발생 시 로직 (기존 기기 정보는 자동으로 덮어씌워짐)
            System.out.println("⚠️ 유저 [" + userId + "]의 이전 단말 [" + existingDevice + "] 로그인을 만료 처리합니다.");
        }

        // 2. 새로운 단말 정보 저장 (예: 30분 유효기간 설정)
        redisTemplate.opsForValue().set(key, deviceId, 30, TimeUnit.MINUTES);
    }
}
