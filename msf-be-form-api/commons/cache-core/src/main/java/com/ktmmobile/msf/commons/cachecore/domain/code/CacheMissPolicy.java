package com.ktmmobile.msf.commons.cachecore.domain.code;

/**
 * 캐시 미스 처리 정책
 */
public enum CacheMissPolicy {
    // 캐시 미스 시 추가 처리 없음
    NONE,

    // 캐시 미스 시 단일 키만 적재
    LOAD_ONE,

    // 캐시 미스 시 전체 캐시 재적재
    RELOAD_ALL,
    
    // 단일 키 적재 후 없으면 전체 캐시 재적재
    LOAD_ONE_THEN_RELOAD_ALL
}
