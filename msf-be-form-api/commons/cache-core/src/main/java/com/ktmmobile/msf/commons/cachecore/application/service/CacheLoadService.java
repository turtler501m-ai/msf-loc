package com.ktmmobile.msf.commons.cachecore.application.service;

import java.time.Instant;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.cachecore.application.dto.CacheMetadata;
import com.ktmmobile.msf.commons.cachecore.application.dto.CacheLoadResult;
import com.ktmmobile.msf.commons.cachecore.application.port.out.CacheLoader;
import com.ktmmobile.msf.commons.cachecore.domain.code.CacheStoreType;
import com.ktmmobile.msf.commons.cachecore.support.exception.CacheException;
import com.ktmmobile.msf.commons.cachecore.support.util.CacheKeyGenerator;
import com.ktmmobile.msf.commons.cachecore.support.util.CacheLoadDistributedLock;
import com.ktmmobile.msf.commons.cachecore.support.util.CacheLoadStampedeGuard;
import com.ktmmobile.msf.commons.cachecore.support.util.CacheLoadTimeFormatter;
import com.ktmmobile.msf.commons.cachecore.support.util.CacheMetadataKeyGenerator;
import com.ktmmobile.msf.commons.cachecore.support.util.CacheRedisWriteLock;
import com.ktmmobile.msf.commons.cachecore.support.properties.CacheProperties;
import com.ktmmobile.msf.commons.cachecore.support.store.CacheStoreReader;
import com.ktmmobile.msf.commons.cachecore.support.store.CacheStoreWriter;

/**
 * 캐시 적재와 Redis 반영 처리 서비스
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CacheLoadService {

    private static final String FULL_LOAD_TYPE = "FULL";
    private static final String SINGLE_LOAD_TYPE = "SINGLE";

    private final CacheRegistry cacheRegistry;
    private final CacheLocalStore cacheLocalStore;
    private final CacheLoadDistributedLock cacheLoadDistributedLock;
    private final CacheLoadStampedeGuard cacheLoadStampedeGuard;
    private final CacheRedisWriteLock cacheRedisWriteLock;
    private final CacheKeyGenerator cacheKeyGenerator;
    private final CacheLoadTimeFormatter cacheLoadTimeFormatter;
    private final CacheMetadataKeyGenerator cacheMetadataKeyGenerator;
    private final CacheProperties cacheProperties;
    private final CacheStoreReader cacheStoreReader;
    private final CacheStoreWriter cacheStoreWriter;

    /**
     * 지정 캐시 강제 적재
     *
     * @param cacheName 캐시 이름
     * @return 캐시 적재 결과
     */
    public CacheLoadResult load(String cacheName) {
        try {
            CacheLoader<?> cacheLoader = cacheRegistry.getRequired(cacheName);
            return cacheLoadStampedeGuard.execute(
                cacheName,
                () -> isCacheAvailable(cacheLoader),
                () -> loadWithDistributedLock(cacheLoader)
            );
        } catch (RuntimeException ex) {
            throw CacheException.wrap("Cache load failed. cacheName=" + cacheName, ex);
        }
    }

    /**
     * 캐시가 없을 때만 지정 캐시 적재
     *
     * @param cacheName 캐시 이름
     * @return 캐시 적재 결과
     */
    public CacheLoadResult loadIfAbsent(String cacheName) {
        try {
            CacheLoader<?> cacheLoader = cacheRegistry.getRequired(cacheName);
            if (isCacheAvailable(cacheLoader)) {
                return CacheLoadResult.skipped(cacheName, "Cache already available.");
            }

            return cacheLoadStampedeGuard.execute(
                cacheName,
                () -> isCacheAvailable(cacheLoader),
                () -> {
                    if (isCacheAvailable(cacheLoader)) {
                        return CacheLoadResult.skipped(cacheName, "Cache already available.");
                    }
                    return loadWithDistributedLock(cacheLoader);
                }
            );
        } catch (RuntimeException ex) {
            throw CacheException.wrap("Cache load failed. cacheName=" + cacheName, ex);
        }
    }

    /**
     * 지정 캐시 키 값 적재
     *
     * @param cacheName 캐시 이름
     * @param key 캐시 키
     * @return 캐시 값
     */
    public Optional<Object> load(String cacheName, String key) {
        try {
            CacheLoader<?> cacheLoader = cacheRegistry.getRequired(cacheName);
            return cacheLoadStampedeGuard.executeValue(
                cacheLoader.cacheName() + ":" + key,
                () -> isCacheValueAvailable(cacheLoader, key),
                () -> loadOne(cacheLoader, key)
            );
        } catch (RuntimeException ex) {
            throw CacheException.wrap("Cache value load failed. cacheName=" + cacheName + ", key=" + key, ex);
        }
    }

    private CacheLoadResult load(CacheLoader<?> cacheLoader) {
        Instant startedAt = Instant.now();
        try {
            log.info(">>> Cache Load Start. cacheName={}", cacheLoader.cacheName());
            Map<String, ?> values = cacheLoader.load();
            Map<String, Object> cacheValues = toCacheValues(values);
            Instant finishedAt = Instant.now();
            CacheMetadata metadata = createMetadata(
                cacheLoader,
                FULL_LOAD_TYPE,
                values.size(),
                startedAt,
                finishedAt
            );
            cacheLocalStore.replace(cacheLoader, cacheValues);
            cacheLocalStore.setMetadata(cacheLoader, metadata);
            writeCacheValuesToRedisLazily(cacheLoader, cacheValues, metadata);

            CacheLoadResult result = CacheLoadResult.success(
                cacheLoader.cacheName(),
                values.size(),
                startedAt,
                finishedAt
            );
            log.info(">>> Cache Load Success. cacheName={}, count={}, elapsed={}ms",
                result.cacheName(), result.count(), result.elapsed().toMillis());
            return result;
        } catch (Exception ex) {
            throw CacheException.wrap("Cache load failed. cacheName=" + cacheLoader.cacheName(), ex);
        }
    }

    private CacheLoadResult loadWithDistributedLock(CacheLoader<?> cacheLoader) {
        return cacheLoadDistributedLock.execute(cacheLoader.cacheName(), () -> load(cacheLoader))
            .orElseGet(() -> waitAndLoadLocalFromRedis(cacheLoader));
    }

    private CacheLoadResult waitAndLoadLocalFromRedis(CacheLoader<?> cacheLoader) {
        Instant startedAt = Instant.now();
        if (cacheLoader.storeType() != CacheStoreType.HASH) {
            return CacheLoadResult.skipped(cacheLoader.cacheName(),
                "Cache load skipped by distributed lock. Value cache cannot be hydrated without a key.");
        }

        long count = waitForRedisHashSize(cacheLoader);
        if (count <= 0L) {
            return CacheLoadResult.skipped(cacheLoader.cacheName(),
                "Cache load skipped by distributed lock and Redis cache was not ready.");
        }

        Instant finishedAt = Instant.now();
        CacheLoadResult result = CacheLoadResult.success(
            cacheLoader.cacheName(),
            toResultCount(count),
            startedAt,
            finishedAt
        );
        log.info(">>> Cache Redis Cache Ready. cacheName={}, count={}, elapsed={}ms",
            result.cacheName(), result.count(), result.elapsed().toMillis());
        return result;
    }

    private long waitForRedisHashSize(CacheLoader<?> cacheLoader) {
        Instant deadline = Instant.now().plus(cacheProperties.loadLock().waitTimeout());
        while (Instant.now().isBefore(deadline)) {
            long size = cacheStoreReader.getHashSize(cacheLoader.cacheName());
            if (size > 0L) {
                return size;
            }
            sleepBeforeRedisHydrationRetry();
        }
        return 0L;
    }

    private void sleepBeforeRedisHydrationRetry() {
        try {
            Thread.sleep(cacheProperties.loadLock().retryInterval().toMillis());
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new CacheException("Interrupted while waiting for Redis cache hydration.", ex);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> toCacheValues(Map<String, ?> values) {
        return (Map<String, Object>) values;
    }

    private void writeCacheValuesToRedisLazily(
        CacheLoader<?> cacheLoader,
        Map<String, Object> cacheValues,
        CacheMetadata metadata
    ) {
        Map<String, Object> redisCacheValues = normalizeValuesForRedis(cacheValues);
        CompletableFuture.runAsync(() -> {
                Instant startedAt = Instant.now();
                boolean written = cacheRedisWriteLock.execute(
                    cacheLoader.cacheName(),
                    () -> replaceCacheValues(cacheLoader, redisCacheValues, metadata)
                );
                if (written) {
                    cacheLocalStore.clear(cacheLoader);
                }
                logLazyRedisWriteResult(cacheLoader.cacheName(), redisCacheValues.size(), startedAt, written);
            })
            .exceptionally(ex -> {
                log.warn("Lazy Redis cache write failed. cacheName={}", cacheLoader.cacheName(), ex);
                return null;
            });
    }

    private void replaceCacheValues(CacheLoader<?> cacheLoader, Map<String, Object> cacheValues, CacheMetadata metadata) {
        if (cacheLoader.storeType() == CacheStoreType.HASH) {
            cacheStoreWriter.replaceHashValues(cacheLoader.cacheName(), cacheValues, cacheLoader.ttl());
            setMetadata(cacheLoader, metadata);
            return;
        }

        cacheValues.forEach((key, value) -> setValueCache(cacheLoader, key, value));
        setMetadata(cacheLoader, metadata);
    }

    @SuppressWarnings("unchecked")
    private Optional<Object> loadOne(CacheLoader<?> cacheLoader, String key) {
        Instant startedAt = Instant.now();
        log.info(">>> Cache Value Load Start. cacheName={}, key={}", cacheLoader.cacheName(), key);
        Optional<?> loaded = ((CacheLoader<Object>) cacheLoader).load(key);
        Instant finishedAt = Instant.now();
        loaded.ifPresent(value -> {
            CacheMetadata metadata = createMetadata(
                cacheLoader,
                SINGLE_LOAD_TYPE,
                1,
                startedAt,
                finishedAt
            );
            cacheLocalStore.set(cacheLoader, key, value);
            cacheLocalStore.setMetadata(cacheLoader, metadata);
            writeCacheValueToRedisLazily(cacheLoader, key, value, metadata);
        });
        return loaded.map(value -> (Object) value);
    }

    private void writeCacheValueToRedisLazily(
        CacheLoader<?> cacheLoader,
        String key,
        Object value,
        CacheMetadata metadata
    ) {
        Object redisValue = normalizeValueForRedis(value);
        CompletableFuture.runAsync(() -> {
                Instant startedAt = Instant.now();
                boolean written = cacheRedisWriteLock.execute(
                    cacheLoader.cacheName(),
                    () -> {
                        setCacheValue(cacheLoader, key, redisValue);
                        setMetadata(cacheLoader, metadata);
                    }
                );
                if (written) {
                    cacheLocalStore.clear(cacheLoader, key);
                    cacheLocalStore.clearMetadata(cacheLoader, metadata);
                }
                logLazyRedisValueWriteResult(cacheLoader.cacheName(), key, startedAt, written);
            })
            .exceptionally(ex -> {
                log.warn("Lazy Redis cache value write failed. cacheName={}, key={}",
                    cacheLoader.cacheName(), key, ex);
                return null;
            });
    }

    private void setCacheValue(CacheLoader<?> cacheLoader, String key, Object value) {
        if (cacheLoader.storeType() == CacheStoreType.HASH) {
            setHashCache(cacheLoader, key, value);
            return;
        }
        setValueCache(cacheLoader, key, value);
    }

    private void setHashCache(CacheLoader<?> cacheLoader, String key, Object value) {
        cacheStoreWriter.setHashValue(cacheLoader.cacheName(), key, value, cacheLoader.ttl());
    }

    private void setValueCache(CacheLoader<?> cacheLoader, String key, Object value) {
        String cacheKey = cacheKeyGenerator.generate(cacheLoader.cacheName(), key);
        cacheStoreWriter.setValue(cacheKey, value, cacheLoader.ttl());
    }

    private CacheMetadata createMetadata(
        CacheLoader<?> cacheLoader,
        String loadType,
        int count,
        Instant startedAt,
        Instant finishedAt
    ) {
        LocalDateTime loadedAt = cacheLoadTimeFormatter.toLocalDateTime(finishedAt);
        return new CacheMetadata(
            cacheLoader.cacheName(),
            cacheLoader.storeType().name(),
            loadType,
            count,
            cacheLoadTimeFormatter.format(loadedAt),
            null,
            Duration.between(startedAt, finishedAt).toMillis()
        );
    }

    private int toResultCount(long count) {
        return count > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) count;
    }

    private void setMetadata(CacheLoader<?> cacheLoader, CacheMetadata metadata) {
        cacheStoreWriter.setValue(
            cacheMetadataKeyGenerator.generate(cacheLoader.cacheName()),
            metadata,
            cacheLoader.ttl()
        );
    }

    private void logLazyRedisWriteResult(String cacheName, int count, Instant startedAt, boolean written) {
        long elapsedMillis = Duration.between(startedAt, Instant.now()).toMillis();
        if (written) {
            log.info(">>> Lazy Redis Cache Write Success. cacheName={}, count={}, elapsed={}ms",
                cacheName, count, elapsedMillis);
            return;
        }
        log.info(">>> Lazy Redis Cache Write Skip. cacheName={}, count={}, elapsed={}ms",
            cacheName, count, elapsedMillis);
    }

    private void logLazyRedisValueWriteResult(String cacheName, String key, Instant startedAt, boolean written) {
        long elapsedMillis = Duration.between(startedAt, Instant.now()).toMillis();
        if (written) {
            log.info(">>> Lazy Redis Cache Value Write Success. cacheName={}, key={}, elapsed={}ms",
                cacheName, key, elapsedMillis);
            return;
        }
        log.info(">>> Lazy Redis Cache Value Write Skip. cacheName={}, key={}, elapsed={}ms",
            cacheName, key, elapsedMillis);
    }

    private Map<String, Object> normalizeValuesForRedis(Map<String, Object> values) {
        Map<String, Object> normalizedValues = new LinkedHashMap<>();
        values.forEach((key, value) -> normalizedValues.put(key, normalizeValueForRedis(value)));
        return normalizedValues;
    }

    private Object normalizeValueForRedis(Object value) {
        if (value instanceof Map<?, ?> map) {
            Map<Object, Object> normalizedMap = new LinkedHashMap<>();
            map.forEach((mapKey, mapValue) -> normalizedMap.put(mapKey, normalizeValueForRedis(mapValue)));
            return normalizedMap;
        }
        if (value instanceof Set<?> set) {
            return new LinkedHashSet<>(set.stream()
                .map(this::normalizeValueForRedis)
                .toList());
        }
        if (value instanceof Collection<?> collection) {
            return new ArrayList<>(collection.stream()
                .map(this::normalizeValueForRedis)
                .toList());
        }
        return value;
    }

    private boolean isCacheAvailable(CacheLoader<?> cacheLoader) {
        if (cacheLocalStore.hasCache(cacheLoader)) {
            return true;
        }
        if (cacheLoader.storeType() != CacheStoreType.HASH) {
            return false;
        }

        try {
            long count = cacheStoreReader.getHashSize(cacheLoader.cacheName());
            boolean available = count > 0L;
            if (available) {
                log.info(">>> Cache Available From Redis. cacheName={}, count={}",
                    cacheLoader.cacheName(), count);
            }
            return available;
        } catch (RuntimeException ex) {
            log.warn("Cache availability check failed. cacheName={}", cacheLoader.cacheName(), ex);
            return false;
        }
    }

    private boolean isCacheValueAvailable(CacheLoader<?> cacheLoader, String key) {
        if (cacheLocalStore.hasKey(cacheLoader, key)) {
            return true;
        }
        if (cacheLoader.storeType() == CacheStoreType.HASH) {
            return cacheStoreReader.hasHashKey(cacheLoader.cacheName(), key);
        }
        return cacheStoreReader.hasValueKey(cacheKeyGenerator.generate(cacheLoader.cacheName(), key));
    }
}
