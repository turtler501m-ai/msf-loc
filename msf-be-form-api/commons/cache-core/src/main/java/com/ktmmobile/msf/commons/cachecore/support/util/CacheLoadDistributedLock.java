package com.ktmmobile.msf.commons.cachecore.support.util;

import java.time.Instant;
import java.util.Optional;
import java.util.function.Supplier;

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
 * 캐시 적재용 분산 락 처리기
 */
@Slf4j
@Component
public class CacheLoadDistributedLock {

    private static final String LOCK_NAME_PREFIX = "cache-load:";

    private final ObjectProvider<LockProvider> lockProvider;
    private final CacheProperties cacheProperties;

    /**
     * 캐시 적재용 분산 락 처리기 생성
     */
    public CacheLoadDistributedLock(
        @Qualifier(CacheCoreRedisConfig.CACHE_LOAD_LOCK_PROVIDER) ObjectProvider<LockProvider> lockProvider,
        CacheProperties cacheProperties
    ) {
        this.lockProvider = lockProvider;
        this.cacheProperties = cacheProperties;
    }

    /**
     * 분산 락 획득 후 작업 실행
     */
    public <T> Optional<T> execute(String cacheName, Supplier<T> task) {
        if (!cacheProperties.loadLock().isEnabled()) {
            return Optional.of(task.get());
        }

        LockProvider provider = lockProvider.getIfAvailable();
        if (provider == null) {
            log.debug("Cache load lock provider is not available. cacheName={}", cacheName);
            return Optional.of(task.get());
        }

        LockConfiguration lockConfiguration = new LockConfiguration(
            Instant.now(),
            LOCK_NAME_PREFIX + cacheName,
            cacheProperties.loadLock().lockAtMostFor(),
            cacheProperties.loadLock().lockAtLeastFor()
        );

        Optional<SimpleLock> lock = provider.lock(lockConfiguration);
        if (lock.isEmpty()) {
            log.info("Skip cache data load because another instance owns the lock. cacheName={}", cacheName);
            return Optional.empty();
        }

        try {
            return Optional.of(task.get());
        } finally {
            lock.get().unlock();
        }
    }
}
