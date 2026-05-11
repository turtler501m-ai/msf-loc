package com.ktmmobile.msf.commons.cachecore.domain.code;

/**
 * 캐시 저장 방식
 */
public enum CacheStoreType {
    // Redis Hash 구조로 저장
    HASH,

    // Redis Value 구조로 저장
    VALUE
}
