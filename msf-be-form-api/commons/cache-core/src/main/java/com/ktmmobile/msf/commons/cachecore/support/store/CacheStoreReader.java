package com.ktmmobile.msf.commons.cachecore.support.store;

import java.util.Map;

/**
 * 캐시 저장소 조회 기능
 */
public interface CacheStoreReader {

    /**
     * Hash 캐시 전체 항목 조회
     *
     * @param key 캐시 키
     * @return Hash 캐시 항목
     */
    Map<String, Object> getHashEntries(String key);

    /**
     * Hash 캐시 항목 수 조회
     *
     * @param key 캐시 키
     * @return 항목 수
     */
    long getHashSize(String key);

    /**
     * Hash 캐시 키 존재 여부 확인
     *
     * @param key 캐시 키
     * @param hashKey Hash 키
     * @return 존재 여부
     */
    boolean hasHashKey(String key, String hashKey);

    /**
     * Value 캐시 키 존재 여부 확인
     *
     * @param key 캐시 키
     * @return 존재 여부
     */
    boolean hasValueKey(String key);
}
