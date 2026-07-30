package com.ktmmobile.msf.commons.cachecore.support.config;

import java.time.Duration;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.cachecore.application.dto.CacheLoadResult;
import com.ktmmobile.msf.commons.cachecore.application.port.in.CacheReader;
import com.ktmmobile.msf.commons.cachecore.application.port.out.CacheLoader;
import com.ktmmobile.msf.commons.cachecore.application.service.CacheLoadService;
import com.ktmmobile.msf.commons.cachecore.application.service.CacheRegistry;
import com.ktmmobile.msf.commons.cachecore.domain.code.CacheLoadFailurePolicy;
import com.ktmmobile.msf.commons.cachecore.support.exception.CacheException;
import com.ktmmobile.msf.commons.cachecore.support.properties.CacheProperties;
import com.ktmmobile.msf.commons.common.utils.env.EnvironmentUtils;

/**
 * 애플리케이션 시작 시 캐시 자동 적재 실행
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class CacheAutoLoadRunner implements SmartInitializingSingleton {

    private final CacheProperties cacheProperties;
    private final CacheRegistry cacheRegistry;
    private final CacheLoadService cacheLoadService;
    private final CacheReader cacheReader;

    /** 전체 싱글톤 초기화 이후 캐시 자동 적재 */
    @Override
    public void afterSingletonsInstantiated() {
        if (!cacheProperties.autoLoad().isEnabled()) {
            log.info(">>> Cache Auto Load Disabled.");
            return;
        }

        cacheRegistry.getAll()
            .stream()
            .filter(cacheLoader -> !cacheProperties.autoLoad().isExcluded(cacheLoader.cacheName()))
            .forEach(this::load);
    }

    private void load(CacheLoader<?> cacheLoader) {
        try {
            // 캐시별 시작 적재 모드에 따라 적재 방식 선택
            CacheLoadResult result = loadByStartupPolicy(cacheLoader);
            log.info(">>> Cache Auto Load Result. cacheName={}, mode={}, status={}, count={}, message={}",
                result.cacheName(), cacheLoader.startupLoadMode(), result.status(), result.count(), result.message());
        } catch (CacheException e) {
            // 실패 시 경고만 남기고 다음 캐시 적재 계속 진행
            if (cacheLoader.failurePolicy() == CacheLoadFailurePolicy.WARN_AND_CONTINUE) {
                log.warn(">>> Cache Auto Load Failed. cacheName={}", cacheLoader.cacheName(), e);
                return;
            }
            // 실패 즉시 중단 정책은 예외 전파
            throw e;
        }
    }

    private CacheLoadResult loadByStartupPolicy(CacheLoader<?> cacheLoader) {
        if (shouldForceLoadByStartupReloadAfter(cacheLoader)) {
            return cacheLoadService.load(cacheLoader.cacheName());
        }

        return switch (cacheLoader.startupLoadMode()) {
            case LOAD_IF_ABSENT -> cacheLoadService.loadIfAbsent(cacheLoader.cacheName());
            case FORCE_LOAD -> cacheLoadService.load(cacheLoader.cacheName());
        };
    }

    private boolean shouldForceLoadByStartupReloadAfter(CacheLoader<?> cacheLoader) {
        if (EnvironmentUtils.isLocal()) {
            return false;
        }

        return cacheLoader.startupReloadAfter()
            .map(reloadAfter -> shouldForceLoadByStartupReloadAfter(cacheLoader, reloadAfter))
            .orElse(false);
    }

    private boolean shouldForceLoadByStartupReloadAfter(CacheLoader<?> cacheLoader, Duration reloadAfter) {
        try {
            return cacheReader.getLoadTime(cacheLoader.cacheName())
                .map(loadedAt -> shouldForceLoadByStartupReloadAfter(cacheLoader, loadedAt, reloadAfter))
                .orElseGet(() -> shouldForceLoadByMissingLoadTime(cacheLoader, reloadAfter));
        } catch (CacheException e) {
            log.warn(">>> Cache Auto Load Time Check Failed. cacheName={}", cacheLoader.cacheName(), e);
            return true;
        }
    }

    private boolean shouldForceLoadByStartupReloadAfter(
        CacheLoader<?> cacheLoader,
        LocalDateTime loadedAt,
        Duration reloadAfter
    ) {
        boolean expired = !loadedAt.plus(reloadAfter).isAfter(LocalDateTime.now());
        if (expired) {
            log.info(">>> Cache Auto Load Stale Metadata. cacheName={}, loadedAt={}, reloadAfter={}",
                cacheLoader.cacheName(), loadedAt, reloadAfter);
        }
        return expired;
    }

    private boolean shouldForceLoadByMissingLoadTime(CacheLoader<?> cacheLoader, Duration reloadAfter) {
        log.info(">>> Cache Auto Load Missing Load Time. cacheName={}, reloadAfter={}",
            cacheLoader.cacheName(), reloadAfter);
        return true;
    }
}
