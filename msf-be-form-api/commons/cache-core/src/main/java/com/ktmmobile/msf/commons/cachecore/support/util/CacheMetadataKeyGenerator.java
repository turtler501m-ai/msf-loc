package com.ktmmobile.msf.commons.cachecore.support.util;

import org.springframework.stereotype.Component;

/**
 * 캐시 메타데이터 키 생성기
 */
@Component
public class CacheMetadataKeyGenerator {

    private static final String METADATA_KEY_SUFFIX = "-metadata";

    /** 캐시 메타데이터 키 생성 */
    public String generate(String cacheName) {
        return cacheName + METADATA_KEY_SUFFIX;
    }
}
