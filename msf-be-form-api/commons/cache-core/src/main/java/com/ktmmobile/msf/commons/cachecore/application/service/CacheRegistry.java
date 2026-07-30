package com.ktmmobile.msf.commons.cachecore.application.service;

import java.time.Duration;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.cachecore.application.port.out.CacheExtensionLoader;
import com.ktmmobile.msf.commons.cachecore.application.port.out.CacheLoader;
import com.ktmmobile.msf.commons.cachecore.domain.code.CacheStoreType;
import com.ktmmobile.msf.commons.cachecore.support.exception.CacheException;

/**
 * CacheLoader Bean 이름 기준 관리
 */
@RequiredArgsConstructor
@Component
public class CacheRegistry implements InitializingBean {

    private final List<CacheLoader<?>> cacheLoaders;
    private final Map<String, CacheLoader<?>> cacheLoadersByName = new LinkedHashMap<>();
    private final Map<String, CacheLoader<?>> primaryCacheLoadersByName = new LinkedHashMap<>();

    /** CacheLoader 목록 정렬 및 등록 */
    @Override
    public void afterPropertiesSet() {
        List<CacheLoader<?>> sortedLoaders = cacheLoaders.stream()
            .sorted(AnnotationAwareOrderComparator.INSTANCE)
            .toList();

        for (CacheLoader<?> cacheLoader: sortedLoaders) {
            registerCacheLoader(cacheLoader);
            primaryCacheLoadersByName.put(cacheLoader.cacheName(), cacheLoader);
            registerExtensionCacheLoaders(cacheLoader);
        }
    }

    /**
     * 지정 이름의 CacheLoader 필수 조회
     *
     * @param cacheName 캐시 이름
     * @return CacheLoader
     */
    public CacheLoader<?> getRequired(String cacheName) {
        CacheLoader<?> cacheLoader = cacheLoadersByName.get(cacheName);
        if (cacheLoader == null) {
            throw new CacheException("Cache loader not found. cacheName=" + cacheName);
        }
        return cacheLoader;
    }

    /**
     * 등록된 전체 CacheLoader 조회
     *
     * @return CacheLoader 목록
     */
    public Collection<CacheLoader<?>> getAll() {
        return List.copyOf(primaryCacheLoadersByName.values());
    }

    private void registerCacheLoader(CacheLoader<?> cacheLoader) {
        CacheLoader<?> previous = cacheLoadersByName.putIfAbsent(cacheLoader.cacheName(), cacheLoader);
        if (previous != null) {
            throw new CacheException("Duplicate cache loader name. cacheName=" + cacheLoader.cacheName());
        }
    }

    private <S> void registerExtensionCacheLoaders(CacheLoader<S> cacheLoader) {
        for (CacheExtensionLoader<S, ?> extensionLoader: cacheLoader.extensionLoaders()) {
            registerCacheLoader(new SourceCacheExtensionLoader<>(cacheLoader, extensionLoader));
        }
    }

    private record SourceCacheExtensionLoader<S, V>(
        CacheLoader<S> sourceLoader,
        CacheExtensionLoader<S, V> extensionLoader
    ) implements CacheLoader<V> {

        @Override
        public String cacheName() {
            return extensionLoader.cacheName();
        }

        @Override
        public CacheStoreType storeType() {
            return extensionLoader.storeType();
        }

        @Override
        public Duration ttl() {
            return extensionLoader.ttl();
        }

        @Override
        public Map<String, V> load() {
            return extensionLoader.load(sourceLoader.load());
        }
    }
}
