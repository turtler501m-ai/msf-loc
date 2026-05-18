package com.ktmmobile.msf.commons.cachecore.support.util;

import java.time.Instant;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.core.SimpleLock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.cachecore.support.config.CacheCoreRedisConfig;
import com.ktmmobile.msf.commons.cachecore.support.properties.CacheProperties;

/**
 * Redis 캐시 쓰기 락 처리기
 */
@Slf4j
@Component
public class CacheRedisWriteLock {

    private static final String LOCK_NAME_PREFIX = "cache-redis-write:";

    private final ObjectProvider<LockProvider> lockProvider;
    private final CacheProperties cacheProperties;

    /** Redis 캐시 쓰기 락 처리기 생성 */
    public CacheRedisWriteLock(
        @Qualifier(CacheCoreRedisConfig.CACHE_LOAD_LOCK_PROVIDER) ObjectProvider<LockProvider> lockProvider,
        CacheProperties cacheProperties
    ) {
        this.lockProvider = lockProvider;
        this.cacheProperties = cacheProperties;
    }

    /** Redis 쓰기 락 획득 후 작업 실행 */
    public boolean execute(String cacheName, Runnable task) {
        if (!cacheProperties.redisWriteLock().isEnabled()) {
            task.run();
            return true;
        }

        LockProvider provider = lockProvider.getIfAvailable();
        if (provider == null) {
            log.debug("Cache Redis write lock provider is not available. cacheName={}", cacheName);
            task.run();
            return true;
        }

        LockConfiguration lockConfiguration = new LockConfiguration(
            Instant.now(),
            LOCK_NAME_PREFIX + cacheName,
            cacheProperties.redisWriteLock().lockAtMostFor(),
            cacheProperties.redisWriteLock().lockAtLeastFor()
        );

        Optional<SimpleLock> lock = provider.lock(lockConfiguration);
        if (lock.isEmpty()) {
            log.info("Skip Redis cache write because another instance owns the lock. cacheName={}", cacheName);
            return false;
        }

        try {
            task.run();
            return true;
        } finally {
            lock.get().unlock();
        }
    }
}
