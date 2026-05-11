package com.ktmmobile.msf.commons.cachecore.application.port.out;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

import com.ktmmobile.msf.commons.cachecore.domain.code.CacheLoadFailurePolicy;
import com.ktmmobile.msf.commons.cachecore.domain.code.CacheMissPolicy;
import com.ktmmobile.msf.commons.cachecore.domain.code.CacheStoreType;
import com.ktmmobile.msf.commons.cachecore.domain.code.StartupCacheLoadMode;

/**
 * 캐시 원천 데이터 적재 아웃바운드 포트
 */
public interface CacheLoader<V> {

    /**
     * 캐시 이름 반환
     *
     * @return 캐시 이름
     */
    String cacheName();

    /**
     * 캐시 저장 방식 반환
     *
     * @return 캐시 저장 방식
     */
    default CacheStoreType storeType() {
        return CacheStoreType.HASH;
    }

    /**
     * 전체 캐시 데이터 적재
     *
     * @return 캐시 데이터
     */
    Map<String, V> load();

    /**
     * 단일 키 캐시 데이터 적재
     *
     * @param key 캐시 키
     * @return 캐시 데이터
     */
    default Optional<V> load(String key) {
        return Optional.empty();
    }

    /**
     * 캐시 미스 처리 정책 반환
     *
     * @return 캐시 미스 처리 정책
     */
    default CacheMissPolicy missPolicy() {
        return CacheMissPolicy.NONE;
    }

    /**
     * 캐시 만료 시간 반환
     *
     * @return 캐시 만료 시간
     */
    default Duration ttl() {
        return null;
    }

    /**
     * 캐시 적재 실패 처리 정책 반환
     *
     * @return 캐시 적재 실패 처리 정책
     */
    default CacheLoadFailurePolicy failurePolicy() {
        return CacheLoadFailurePolicy.FAIL_FAST;
    }

    /**
     * 애플리케이션 시작 시 캐시 적재 방식 반환
     *
     * @return 시작 시 캐시 적재 방식
     */
    default StartupCacheLoadMode startupLoadMode() {
        return StartupCacheLoadMode.LOAD_IF_ABSENT;
    }
}
