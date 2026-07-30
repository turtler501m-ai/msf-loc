package com.ktmmobile.msf.commons.cachecore.support.util;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.cachecore.application.dto.CacheLoadResult;
import com.ktmmobile.msf.commons.cachecore.support.exception.CacheException;
import com.ktmmobile.msf.commons.cachecore.support.properties.CacheProperties;

/**
 * 동일 키 캐시 적재 중복 실행 방지 처리기
 */
@RequiredArgsConstructor
@Component
public class CacheLoadStampedeGuard {

    private final CacheProperties cacheProperties;
    private final ConcurrentMap<String, ReentrantLock> locks = new ConcurrentHashMap<>();

    /** 캐시 전체 적재 중복 실행 방지 */
    public CacheLoadResult execute(String cacheName, BooleanSupplier loaded, Supplier<CacheLoadResult> loader) {
        ReentrantLock lock = getLock(cacheName);
        if (tryLock(lock)) {
            return loadWithLock(lock, loader);
        }

        waitForCurrentLoad(cacheName, lock, loaded);
        return CacheLoadResult.skipped(cacheName, "Cache load already in progress.");
    }

    /** 캐시 단일 값 적재 중복 실행 방지 */
    public <V> Optional<V> executeValue(String key, BooleanSupplier loaded, Supplier<Optional<V>> loader) {
        ReentrantLock lock = getLock(key);
        if (tryLock(lock)) {
            return loadValueWithLock(lock, loader);
        }

        waitForCurrentLoad(key, lock, loaded);
        return Optional.empty();
    }

    private ReentrantLock getLock(String key) {
        return locks.computeIfAbsent(key, _ -> new ReentrantLock());
    }

    private boolean tryLock(ReentrantLock lock) {
        return lock.tryLock();
    }

    private CacheLoadResult loadWithLock(ReentrantLock lock, Supplier<CacheLoadResult> loader) {
        try {
            return loader.get();
        } finally {
            lock.unlock();
        }
    }

    private <V> Optional<V> loadValueWithLock(ReentrantLock lock, Supplier<Optional<V>> loader) {
        try {
            return loader.get();
        } finally {
            lock.unlock();
        }
    }

    private void waitForCurrentLoad(String cacheName, ReentrantLock lock, BooleanSupplier loaded) {
        Instant deadline = Instant.now().plus(cacheProperties.stampede().waitTimeout());
        while (Instant.now().isBefore(deadline)) {
            if (loaded.getAsBoolean() || !lock.isLocked()) {
                return;
            }
            sleep();
        }
        throw new CacheException("Cache load wait timeout. cacheName=" + cacheName);
    }

    private void sleep() {
        try {
            Thread.sleep(cacheProperties.stampede().retryInterval().toMillis());
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new CacheException("Interrupted while waiting for cache load.", ex);
        }
    }
}
