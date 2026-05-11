package com.ktmmobile.msf.commons.cachecore.application.dto;

/**
 * 캐시 적재 메타데이터 표현
 */
public record CacheMetadata(
    String cacheName,
    String storeType,
    String loadType,
    int count,
    String loadedAt,
    String checkedAt,
    long loadElapsedMillis
) {
}
