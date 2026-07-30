package com.ktmmobile.msf.commons.cachecore.application.port.in;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import com.ktmmobile.msf.commons.cachecore.application.dto.CacheMetadata;

/**
 * 캐시 데이터 조회 인바운드 포트
 */
public interface CacheReader {

    /**
     * 캐시 값 Optional 조회
     *
     * @param cacheName 캐시 이름
     * @param key 캐시 키
     * @param valueType 값 타입
     * @return 캐시 값
     */
    <V> Optional<V> get(String cacheName, String key, Class<V> valueType);

    /**
     * 단일 캐시 값 Optional 조회
     *
     * @param cacheName 캐시 이름
     * @param valueType 값 타입
     * @return 캐시 값
     */
    <V> Optional<V> get(String cacheName, Class<V> valueType);

    /**
     * 캐시 값 다건 조회
     * <p>
     * HASH 저장 방식은 저장소의 다건 조회 기능을 사용하고,
     * KEY_VALUE 저장 방식은 키별 조회로 대체한다.
     *
     * @param cacheName 캐시 이름
     * @param keys 캐시 키 목록
     * @param valueType 값 타입
     * @return 캐시 값
     */
    <V> Map<String, V> getAll(String cacheName, Collection<String> keys, Class<V> valueType);

    /**
     * 캐시 값 필수 조회
     *
     * @param cacheName 캐시 이름
     * @param key 캐시 키
     * @param valueType 값 타입
     * @return 캐시 값
     */
    <V> V getRequired(String cacheName, String key, Class<V> valueType);

    /**
     * 단일 캐시 값 필수 조회
     *
     * @param cacheName 캐시 이름
     * @param valueType 값 타입
     * @return 캐시 값
     */
    <V> V getRequired(String cacheName, Class<V> valueType);

    /**
     * 캐시 키 존재 여부 확인
     *
     * @param cacheName 캐시 이름
     * @param key 캐시 키
     * @return 존재 여부
     */
    boolean hasKey(String cacheName, String key);

    /**
     * 캐시 메타데이터 Optional 조회
     *
     * @param cacheName 캐시 이름
     * @return 캐시 메타데이터
     */
    Optional<CacheMetadata> getMetadata(String cacheName);

    /**
     * 캐시 메타데이터 필수 조회
     *
     * @param cacheName 캐시 이름
     * @return 캐시 메타데이터
     */
    CacheMetadata getRequiredMetadata(String cacheName);

    /**
     * 캐시 적재 시간 Optional 조회
     *
     * @param cacheName 캐시 이름
     * @return 캐시 적재 시간
     */
    Optional<LocalDateTime> getLoadTime(String cacheName);

    /**
     * 캐시 적재 시간 필수 조회
     *
     * @param cacheName 캐시 이름
     * @return 캐시 적재 시간
     */
    LocalDateTime getRequiredLoadTime(String cacheName);
}
