package com.ktmmobile.msf.commons.common.service;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.common.service.dto.CacheStampedeProtection;
import com.ktmmobile.msf.commons.common.service.port.CacheService;

@Slf4j
@RequiredArgsConstructor
@Service
public class CacheStampedeGuard {

    private final CacheService<String> lockCacheService;

    public <T> T getOrLoad(
        CacheService<T> cacheService,
        CacheStampedeProtection protection,
        Supplier<T> loader,
        Supplier<? extends RuntimeException> unavailableExceptionSupplier
    ) {
        T cached = cacheService.getValue(protection.key());
        if (cached != null) {
            return cached;
        }

        String token = UUID.randomUUID().toString();
        if (lockCacheService.setValueIfAbsent(protection.lockKey(), token, protection.lockTimeToLive())) {
            try {
                T rechecked = cacheService.getValue(protection.key());
                if (rechecked != null) {
                    return rechecked;
                }
                T loaded = loader.get();
                cacheService.setValue(protection.key(), loaded, protection.timeToLive());
                cacheService.setValue(protection.staleKey(), loaded, protection.staleCacheTimeToLive());
                return loaded;
            } finally {
                lockCacheService.deleteIfValueEquals(protection.lockKey(), token);
            }
        }

        return waitForCacheOrStale(cacheService, protection, unavailableExceptionSupplier);
    }

    private <T> T waitForCacheOrStale(
        CacheService<T> cacheService,
        CacheStampedeProtection protection,
        Supplier<? extends RuntimeException> unavailableExceptionSupplier
    ) {
        Instant deadline = Instant.now().plus(protection.lockWaitTime());
        while (Instant.now().isBefore(deadline)) {
            sleep(protection, unavailableExceptionSupplier);
            T cached = cacheService.getValue(protection.key());
            if (cached != null) {
                return cached;
            }
        }

        T stale = cacheService.getValue(protection.staleKey());
        if (stale != null) {
            log.warn("Serving stale cache because cache refresh is still in progress. key={}", protection.key());
            return stale;
        }
        throw unavailableExceptionSupplier.get();
    }

    private void sleep(
        CacheStampedeProtection protection,
        Supplier<? extends RuntimeException> unavailableExceptionSupplier
    ) {
        try {
            Thread.sleep(protection.lockRetryInterval().toMillis());
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw unavailableExceptionSupplier.get();
        }
    }
}
