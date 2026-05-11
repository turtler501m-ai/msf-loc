package com.ktmmobile.msf.commons.cachecore.support.store;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.cachecore.support.util.CacheStoreKeyGenerator;
import com.ktmmobile.msf.commons.common.service.port.CacheService;

/**
 * CacheService 기반 캐시 저장소 조회 구현체
 */
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring.data.redis", name = "enabled", havingValue = "false")
@Component
public class CacheServiceStoreReader implements CacheStoreReader {

    private final CacheService<Object> cacheService;
    private final CacheStoreKeyGenerator cacheStoreKeyGenerator;

    /**
     * Hash 캐시 전체 항목 조회
     *
     * @param key 캐시 키
     * @return Hash 캐시 항목
     */
    @Override
    public Map<String, Object> getHashEntries(String key) {
        return cacheService.getEntries(cacheStoreKeyGenerator.generate(key));
    }

    /**
     * Hash 캐시 항목 수 조회
     *
     * @param key 캐시 키
     * @return 항목 수
     */
    @Override
    public long getHashSize(String key) {
        return cacheService.getEntries(cacheStoreKeyGenerator.generate(key)).size();
    }

    /**
     * Hash 캐시 키 존재 여부 확인
     *
     * @param key 캐시 키
     * @param hashKey Hash 키
     * @return 존재 여부
     */
    @Override
    public boolean hasHashKey(String key, String hashKey) {
        return cacheService.hasKey(cacheStoreKeyGenerator.generate(key), hashKey);
    }

    /**
     * Value 캐시 키 존재 여부 확인
     *
     * @param key 캐시 키
     * @return 존재 여부
     */
    @Override
    public boolean hasValueKey(String key) {
        return cacheService.hasKey(cacheStoreKeyGenerator.generate(key));
    }
}
