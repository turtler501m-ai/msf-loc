package com.ktmmobile.msf.commons.cachecore.support.store;

import java.time.Duration;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.cachecore.support.util.CacheStoreKeyGenerator;
import com.ktmmobile.msf.commons.common.service.port.CacheService;

/**
 * CacheService 기반 캐시 저장소 쓰기 구현체
 */
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring.data.redis", name = "enabled", havingValue = "false")
@Component
public class CacheServiceStoreWriter implements CacheStoreWriter {

    private final CacheService<Object> cacheService;
    private final CacheStoreKeyGenerator cacheStoreKeyGenerator;

    /**
     * Value 캐시 저장
     *
     * @param key 캐시 키
     * @param value 캐시 값
     * @param timeout 만료 시간
     */
    @Override
    public void setValue(String key, Object value, Duration timeout) {
        String storeKey = cacheStoreKeyGenerator.generate(key);
        if (timeout == null) {
            cacheService.setValue(storeKey, value);
            return;
        }
        cacheService.setValue(storeKey, value, timeout);
    }

    /**
     * Hash 캐시 항목 저장
     *
     * @param key 캐시 키
     * @param hashKey Hash 키
     * @param value 캐시 값
     * @param timeout 만료 시간
     */
    @Override
    public void setHashValue(String key, String hashKey, Object value, Duration timeout) {
        String storeKey = cacheStoreKeyGenerator.generate(key);
        if (timeout == null) {
            cacheService.setValue(storeKey, hashKey, value);
            return;
        }
        cacheService.setValue(storeKey, hashKey, value, timeout);
    }

    /**
     * Hash 캐시 전체 항목 교체
     *
     * @param key 캐시 키
     * @param values 캐시 값
     * @param timeout 만료 시간
     */
    @Override
    public void replaceHashValues(String key, Map<String, Object> values, Duration timeout) {
        String storeKey = cacheStoreKeyGenerator.generate(key);
        if (timeout == null) {
            cacheService.replaceValues(storeKey, values);
            return;
        }
        cacheService.replaceValues(storeKey, values, timeout);
    }
}
