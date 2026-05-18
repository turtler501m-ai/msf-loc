package com.ktmmobile.msf.commons.cachecore.support.util;

import org.springframework.stereotype.Component;

/**
 * 캐시 이름과 키 조합 생성기
 */
@Component
public class CacheKeyGenerator {

    private static final String SEPARATOR = ":";

    /** 캐시 이름과 키 조합 생성 */
    public String generate(String cacheName, String key) {
        return cacheName + SEPARATOR + key;
    }
}
