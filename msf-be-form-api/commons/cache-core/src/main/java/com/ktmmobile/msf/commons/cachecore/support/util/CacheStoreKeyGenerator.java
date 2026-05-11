package com.ktmmobile.msf.commons.cachecore.support.util;

import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.cachecore.support.properties.CacheProperties;

/**
 * 실제 캐시 저장소 키 생성기
 */
@Component
public class CacheStoreKeyGenerator {

    private final CacheProperties cacheProperties;

    /**
     * 실제 캐시 저장소 키 생성기 생성
     */
    public CacheStoreKeyGenerator(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    /**
     * 캐시 저장소 키 생성
     */
    public String generate(String key) {
        String keyPrefix = cacheProperties.keyPrefix();
        if (key.startsWith(keyPrefix)) {
            return key;
        }
        return keyPrefix + key;
    }
}
