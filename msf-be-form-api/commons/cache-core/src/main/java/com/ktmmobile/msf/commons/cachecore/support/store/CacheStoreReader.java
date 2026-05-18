package com.ktmmobile.msf.commons.cachecore.support.store;

import java.util.Collection;
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
     * Hash 캐시 항목 다건 조회
     * <p>
     * 반환 값에는 저장소에 존재하는 Hash 키만 포함된다.
     *
     * @param key 캐시 키
     * @param hashKeys Hash 키 목록
     * @return Hash 캐시 항목
     */
    Map<String, Object> getHashValues(String key, Collection<String> hashKeys);

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
