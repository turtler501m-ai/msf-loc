package com.ktmmobile.msf.commons.cachecore.application.port.out;

import java.util.Map;

/**
 * 원천 캐시 데이터 기반 확장 캐시 적재 아웃바운드 포트
 *
 * @param <S> 원천 캐시 값 타입
 * @param <V> 확장 캐시 값 타입
 */
public interface CacheExtensionLoader<S, V> extends CacheLoader<V> {

    /**
     * 원천 캐시 데이터 기반 확장 캐시 데이터 적재
     *
     * @param sourceValues 원천 캐시 데이터
     * @return 확장 캐시 데이터
     */
    Map<String, V> load(Map<String, S> sourceValues);

    /**
     * 확장 캐시 직접 적재 기본값
     *
     * @return 빈 캐시 데이터
     */
    @Override
    default Map<String, V> load() {
        return Map.of();
    }
}
