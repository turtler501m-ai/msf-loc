package com.ktmmobile.msf.commons.cachecore.support.store;

import java.time.Duration;
import java.util.Map;

/**
 * 캐시 저장소 쓰기 기능
 */
public interface CacheStoreWriter {

    /**
     * Value 캐시 저장
     *
     * @param key 캐시 키
     * @param value 캐시 값
     * @param timeout 만료 시간
     */
    void setValue(String key, Object value, Duration timeout);

    /**
     * Hash 캐시 항목 저장
     *
     * @param key 캐시 키
     * @param hashKey Hash 키
     * @param value 캐시 값
     * @param timeout 만료 시간
     */
    void setHashValue(String key, String hashKey, Object value, Duration timeout);

    /**
     * Hash 캐시 전체 항목 교체
     *
     * @param key 캐시 키
     * @param values 캐시 값
     * @param timeout 만료 시간
     */
    void replaceHashValues(String key, Map<String, Object> values, Duration timeout);
}
