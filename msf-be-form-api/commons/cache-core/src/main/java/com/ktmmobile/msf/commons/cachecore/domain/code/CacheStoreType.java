package com.ktmmobile.msf.commons.cachecore.domain.code;

/**
 * 캐시 저장 방식
 */
public enum CacheStoreType {
    HASH,         // Redis Hash 구조
    KEY_VALUE,    // 캐시 키별 Redis Value 구조
    SINGLE_VALUE  // 단일 Redis Value 구조
}
