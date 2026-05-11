package com.ktmmobile.msf.commons.cachecore.domain.code;

/**
 * 애플리케이션 시작 시 캐시 적재 방식
 */
public enum StartupCacheLoadMode {
    // 캐시가 없을 때만 시작 시 적재
    LOAD_IF_ABSENT,
    
    // 시작 시 항상 강제 적재
    FORCE_LOAD
}
