package com.ktmmobile.msf.commons.cachecore.application.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.cachecore.application.dto.CacheMetadata;
import com.ktmmobile.msf.commons.cachecore.application.port.in.CacheReader;
import com.ktmmobile.msf.commons.cachecore.application.port.out.CacheLoader;
import com.ktmmobile.msf.commons.cachecore.domain.code.CacheMissPolicy;
import com.ktmmobile.msf.commons.cachecore.domain.code.CacheStoreType;
import com.ktmmobile.msf.commons.cachecore.support.exception.CacheException;
import com.ktmmobile.msf.commons.cachecore.support.util.CacheKeyGenerator;
import com.ktmmobile.msf.commons.cachecore.support.util.CacheLoadTimeFormatter;
import com.ktmmobile.msf.commons.cachecore.support.util.CacheMetadataKeyGenerator;
import com.ktmmobile.msf.commons.cachecore.support.util.CacheStoreKeyGenerator;
import com.ktmmobile.msf.commons.common.service.port.CacheService;

/**
 * 캐시 조회 인바운드 포트 기본 구현체
 */
@Slf4j
@RequiredArgsConstructor
@Service
class DefaultCacheReader implements CacheReader {

    private final CacheService<Object> cacheService;
    private final CacheRegistry cacheRegistry;
    private final CacheLoadService cacheLoadService;
    private final CacheLocalStore cacheLocalStore;
    private final CacheKeyGenerator cacheKeyGenerator;
    private final CacheLoadTimeFormatter cacheLoadTimeFormatter;
    private final CacheMetadataKeyGenerator cacheMetadataKeyGenerator;
    private final CacheStoreKeyGenerator cacheStoreKeyGenerator;

    /**
     * 캐시 값 Optional 조회
     *
     * @param cacheName 캐시 이름
     * @param key 캐시 키
     * @param valueType 값 타입
     * @return 캐시 값
     */
    @Override
    public <V> Optional<V> get(String cacheName, String key, Class<V> valueType) {
        try {
            CacheLoader<?> cacheLoader = cacheRegistry.getRequired(cacheName);
            Object value = getCachedValueOrLoadOnSerializationFailure(cacheLoader, key);
            if (value == null) {
                value = loadOnMiss(cacheLoader, key);
            }
            return Optional.ofNullable(value)
                .map(valueType::cast);
        } catch (RuntimeException ex) {
            throw CacheException.wrap("Cache read failed. cacheName=" + cacheName + ", key=" + key, ex);
        }
    }

    /**
     * 캐시 값 필수 조회
     *
     * @param cacheName 캐시 이름
     * @param key 캐시 키
     * @param valueType 값 타입
     * @return 캐시 값
     */
    @Override
    public <V> V getRequired(String cacheName, String key, Class<V> valueType) {
        return get(cacheName, key, valueType)
            .orElseThrow(() -> new CacheException(
                "Cache value not found. cacheName=" + cacheName + ", key=" + key));
    }

    /**
     * 캐시 키 존재 여부 확인
     *
     * @param cacheName 캐시 이름
     * @param key 캐시 키
     * @return 존재 여부
     */
    @Override
    public boolean hasKey(String cacheName, String key) {
        try {
            CacheLoader<?> cacheLoader = cacheRegistry.getRequired(cacheName);
            if (cacheLocalStore.hasKey(cacheLoader, key)) {
                return true;
            }
            if (cacheLoader.storeType() == CacheStoreType.HASH) {
                return cacheService.hasKey(cacheStoreKeyGenerator.generate(cacheName), key);
            }
            return cacheService.hasKey(cacheStoreKeyGenerator.generate(cacheKeyGenerator.generate(cacheName, key)));
        } catch (RuntimeException ex) {
            throw CacheException.wrap("Cache key check failed. cacheName=" + cacheName + ", key=" + key, ex);
        }
    }

    /**
     * 캐시 메타데이터 조회
     *
     * @param cacheName 캐시 이름
     * @return 캐시 메타데이터
     */
    @Override
    public Optional<CacheMetadata> getMetadata(String cacheName) {
        try {
            CacheLoader<?> cacheLoader = cacheRegistry.getRequired(cacheName);
            Optional<CacheMetadata> localMetadata = cacheLocalStore.getMetadata(cacheLoader);
            if (localMetadata.isPresent()) {
                return localMetadata;
            }

            Object value = cacheService.getValue(cacheStoreKeyGenerator.generate(
                cacheMetadataKeyGenerator.generate(cacheName)));
            return toMetadata(cacheLoader, value);
        } catch (RuntimeException ex) {
            throw CacheException.wrap("Cache metadata read failed. cacheName=" + cacheName, ex);
        }
    }

    /**
     * 캐시 메타데이터 필수 조회
     *
     * @param cacheName 캐시 이름
     * @return 캐시 메타데이터
     */
    @Override
    public CacheMetadata getRequiredMetadata(String cacheName) {
        return getMetadata(cacheName)
            .orElseThrow(() -> new CacheException("Cache metadata not found. cacheName=" + cacheName));
    }

    /**
     * 캐시 적재 시간 조회
     *
     * @param cacheName 캐시 이름
     * @return 캐시 적재 시간
     */
    @Override
    public Optional<LocalDateTime> getLoadTime(String cacheName) {
        try {
            return getMetadata(cacheName)
                .flatMap(metadata -> toLoadTime(cacheName, metadata.loadedAt()));
        } catch (RuntimeException ex) {
            throw CacheException.wrap("Cache load time read failed. cacheName=" + cacheName, ex);
        }
    }

    /**
     * 캐시 적재 시간 필수 조회
     *
     * @param cacheName 캐시 이름
     * @return 캐시 적재 시간
     */
    @Override
    public LocalDateTime getRequiredLoadTime(String cacheName) {
        return getLoadTime(cacheName)
            .orElseThrow(() -> new CacheException("Cache load time not found. cacheName=" + cacheName));
    }

    private Object loadOnMiss(CacheLoader<?> cacheLoader, String key) {
        return switch (cacheLoader.missPolicy()) {
            case NONE -> null;
            case LOAD_ONE -> cacheLoadService.load(cacheLoader.cacheName(), key).orElse(null);
            case RELOAD_ALL -> reloadAllAndGet(cacheLoader, key);
            case LOAD_ONE_THEN_RELOAD_ALL -> cacheLoadService.load(cacheLoader.cacheName(), key)
                .orElseGet(() -> reloadAllAndGet(cacheLoader, key));
        };
    }

    private Object reloadAllAndGet(CacheLoader<?> cacheLoader, String key) {
        cacheLoadService.load(cacheLoader.cacheName());
        return getCachedValue(cacheLoader, key);
    }

    private Object getCachedValueOrLoadOnSerializationFailure(CacheLoader<?> cacheLoader, String key) {
        try {
            return getCachedValue(cacheLoader, key);
        } catch (RuntimeException ex) {
            if (cacheLoader.missPolicy() == CacheMissPolicy.NONE) {
                throw ex;
            }

            log.warn("Cache value read failed. Try cache miss recovery. cacheName={}, key={}",
                cacheLoader.cacheName(), key, ex);
            return loadOnMiss(cacheLoader, key);
        }
    }

    private Object getCachedValue(CacheLoader<?> cacheLoader, String key) {
        Optional<Object> localValue = cacheLocalStore.get(cacheLoader, key);
        if (localValue.isPresent()) {
            return localValue.get();
        }
        if (cacheLoader.storeType() == CacheStoreType.HASH) {
            return cacheService.getValue(cacheStoreKeyGenerator.generate(cacheLoader.cacheName()), key);
        }
        return cacheService.getValue(cacheStoreKeyGenerator.generate(
            cacheKeyGenerator.generate(cacheLoader.cacheName(), key)));
    }

    private Optional<LocalDateTime> toLoadTime(String cacheName, Object value) {
        if (value == null) {
            return Optional.empty();
        }
        if (value instanceof Instant instant) {
            return Optional.of(cacheLoadTimeFormatter.toLocalDateTime(instant));
        }
        if (value instanceof LocalDateTime localDateTime) {
            return Optional.of(localDateTime);
        }
        if (value instanceof CacheMetadata metadata) {
            return toLoadTime(cacheName, metadata.loadedAt());
        }
        if (value instanceof Map<?, ?> metadata) {
            Object loadedAt = metadata.get("loadedAt");
            if (loadedAt == null) {
                loadedAt = metadata.get("loadTime");
            }
            return toLoadTime(cacheName, loadedAt);
        }
        if (value instanceof String loadTime) {
            return cacheLoadTimeFormatter.parse(loadTime);
        }
        throw new CacheException("Invalid cache load time type. cacheName=" + cacheName
            + ", valueType=" + value.getClass().getName());
    }

    private Optional<CacheMetadata> toMetadata(CacheLoader<?> cacheLoader, Object value) {
        if (value == null) {
            return Optional.empty();
        }
        if (value instanceof CacheMetadata metadata) {
            return Optional.of(metadata);
        }
        if (value instanceof Map<?, ?> metadata) {
            return Optional.of(toMetadata(cacheLoader, metadata));
        }
        return toLoadTime(cacheLoader.cacheName(), value)
            .map(loadedAt -> new CacheMetadata(
                cacheLoader.cacheName(),
                cacheLoader.storeType().name(),
                null,
                0,
                cacheLoadTimeFormatter.format(loadedAt),
                null,
                0
            ));
    }

    private CacheMetadata toMetadata(CacheLoader<?> cacheLoader, Map<?, ?> metadata) {
        Optional<LocalDateTime> loadedAt = toLoadTime(cacheLoader.cacheName(), firstValue(metadata, "loadedAt", "loadTime"));
        Optional<LocalDateTime> checkedAt = toLoadTime(cacheLoader.cacheName(), metadata.get("checkedAt"));
        return new CacheMetadata(
            stringValue(metadata.get("cacheName"), cacheLoader.cacheName()),
            stringValue(metadata.get("storeType"), cacheLoader.storeType().name()),
            stringValue(metadata.get("loadType"), null),
            intValue(metadata.get("count")),
            loadedAt.map(cacheLoadTimeFormatter::format).orElse(null),
            checkedAt.map(cacheLoadTimeFormatter::format).orElse(null),
            longValue(metadata.get("loadElapsedMillis"))
        );
    }

    private Object firstValue(Map<?, ?> metadata, String firstKey, String secondKey) {
        Object value = metadata.get(firstKey);
        return value == null ? metadata.get(secondKey) : value;
    }

    private String stringValue(Object value, String defaultValue) {
        return value == null ? defaultValue : value.toString();
    }

    private int intValue(Object value) {
        if (value instanceof Number number) {
            return number.intValue();
        }
        if (value instanceof String text && !text.isBlank()) {
            return Integer.parseInt(text);
        }
        return 0;
    }

    private long longValue(Object value) {
        if (value instanceof Number number) {
            return number.longValue();
        }
        if (value instanceof String text && !text.isBlank()) {
            return Long.parseLong(text);
        }
        return 0L;
    }
}
