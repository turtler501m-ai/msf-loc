package com.ktmmobile.msf.commons.cachecore.application.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.cachecore.application.dto.CacheMetadata;
import com.ktmmobile.msf.commons.cachecore.application.port.out.CacheLoader;
import com.ktmmobile.msf.commons.cachecore.domain.code.CacheStoreType;
import com.ktmmobile.msf.commons.cachecore.support.util.CacheKeyGenerator;

/**
 * Redis 반영 전후에 사용하는 로컬 캐시 저장소
 */
@Component
public class CacheLocalStore {

    private final ConcurrentMap<String, Map<String, Object>> hashStore = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Object> valueStore = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, CacheMetadata> metadataStore = new ConcurrentHashMap<>();
    private final CacheKeyGenerator cacheKeyGenerator;

    /**
     * 로컬 캐시 저장소 생성
     *
     * @param cacheKeyGenerator 캐시 키 생성기
     */
    public CacheLocalStore(CacheKeyGenerator cacheKeyGenerator) {
        this.cacheKeyGenerator = cacheKeyGenerator;
    }

    /**
     * 로컬 캐시 값 조회
     *
     * @param cacheLoader 캐시 로더
     * @param key 캐시 키
     * @return 로컬 캐시 값
     */
    public Optional<Object> get(CacheLoader<?> cacheLoader, String key) {
        if (cacheLoader.storeType() == CacheStoreType.HASH) {
            return Optional.ofNullable(hashStore.getOrDefault(cacheLoader.cacheName(), Map.of()).get(key));
        }
        return Optional.ofNullable(valueStore.get(cacheKeyGenerator.generate(cacheLoader.cacheName(), key)));
    }

    /**
     * 로컬 캐시 데이터 존재 여부 확인
     *
     * @param cacheLoader 캐시 로더
     * @return 존재 여부
     */
    public boolean hasCache(CacheLoader<?> cacheLoader) {
        if (cacheLoader.storeType() == CacheStoreType.HASH) {
            return hashStore.containsKey(cacheLoader.cacheName());
        }
        return valueStore.keySet().stream()
            .anyMatch(key -> key.startsWith(cacheLoader.cacheName() + ":"));
    }

    /**
     * 로컬 캐시 키 존재 여부 확인
     *
     * @param cacheLoader 캐시 로더
     * @param key 캐시 키
     * @return 존재 여부
     */
    public boolean hasKey(CacheLoader<?> cacheLoader, String key) {
        return get(cacheLoader, key).isPresent();
    }

    /**
     * 로컬 캐시 메타데이터 조회
     *
     * @param cacheLoader 캐시 로더
     * @return 캐시 메타데이터
     */
    public Optional<CacheMetadata> getMetadata(CacheLoader<?> cacheLoader) {
        return Optional.ofNullable(metadataStore.get(cacheLoader.cacheName()));
    }

    /**
     * 로컬 캐시 값 전체 교체
     *
     * @param cacheLoader 캐시 로더
     * @param values 캐시 값
     */
    public void replace(CacheLoader<?> cacheLoader, Map<String, Object> values) {
        if (cacheLoader.storeType() == CacheStoreType.HASH) {
            hashStore.put(cacheLoader.cacheName(), Map.copyOf(values));
            return;
        }

        valueStore.keySet().removeIf(key -> key.startsWith(cacheLoader.cacheName() + ":"));
        values.forEach((key, value) -> set(cacheLoader, key, value));
    }

    /**
     * 로컬 캐시 값 저장
     *
     * @param cacheLoader 캐시 로더
     * @param key 캐시 키
     * @param value 캐시 값
     */
    public void set(CacheLoader<?> cacheLoader, String key, Object value) {
        if (cacheLoader.storeType() == CacheStoreType.HASH) {
            hashStore.compute(cacheLoader.cacheName(), (_, currentValues) -> {
                Map<String, Object> updatedValues = new LinkedHashMap<>(
                    currentValues == null ? Map.of() : currentValues
                );
                updatedValues.put(key, value);
                return Map.copyOf(updatedValues);
            });
            return;
        }
        valueStore.put(cacheKeyGenerator.generate(cacheLoader.cacheName(), key), value);
    }

    /**
     * 로컬 캐시 메타데이터 저장
     *
     * @param cacheLoader 캐시 로더
     * @param metadata 캐시 메타데이터
     */
    public void setMetadata(CacheLoader<?> cacheLoader, CacheMetadata metadata) {
        metadataStore.put(cacheLoader.cacheName(), metadata);
    }

    /**
     * 지정 메타데이터와 일치하는 로컬 캐시 메타데이터 삭제
     *
     * @param cacheLoader 캐시 로더
     * @param metadata 캐시 메타데이터
     */
    public void clearMetadata(CacheLoader<?> cacheLoader, CacheMetadata metadata) {
        metadataStore.remove(cacheLoader.cacheName(), metadata);
    }

    /**
     * 지정 캐시의 로컬 데이터 삭제
     *
     * @param cacheLoader 캐시 로더
     */
    public void clear(CacheLoader<?> cacheLoader) {
        if (cacheLoader.storeType() == CacheStoreType.HASH) {
            hashStore.remove(cacheLoader.cacheName());
            metadataStore.remove(cacheLoader.cacheName());
            return;
        }
        valueStore.keySet().removeIf(key -> key.startsWith(cacheLoader.cacheName() + ":"));
        metadataStore.remove(cacheLoader.cacheName());
    }

    /**
     * 지정 캐시 키의 로컬 데이터 삭제
     *
     * @param cacheLoader 캐시 로더
     * @param key 캐시 키
     */
    public void clear(CacheLoader<?> cacheLoader, String key) {
        if (cacheLoader.storeType() == CacheStoreType.HASH) {
            hashStore.computeIfPresent(cacheLoader.cacheName(), (_, currentValues) -> {
                Map<String, Object> updatedValues = new LinkedHashMap<>(currentValues);
                updatedValues.remove(key);
                return updatedValues.isEmpty() ? null : Map.copyOf(updatedValues);
            });
            return;
        }
        valueStore.remove(cacheKeyGenerator.generate(cacheLoader.cacheName(), key));
    }
}
