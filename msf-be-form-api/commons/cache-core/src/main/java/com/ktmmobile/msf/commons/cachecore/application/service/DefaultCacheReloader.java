package com.ktmmobile.msf.commons.cachecore.application.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.cachecore.application.port.in.CacheReloader;
import com.ktmmobile.msf.commons.cachecore.application.dto.CacheLoadResult;
import com.ktmmobile.msf.commons.cachecore.support.exception.CacheException;

/**
 * 캐시 재적재 인바운드 포트 기본 구현체
 */
@RequiredArgsConstructor
@Service
class DefaultCacheReloader implements CacheReloader {

    private final CacheRegistry cacheRegistry;
    private final CacheLoadService cacheLoadService;

    /**
     * 지정 캐시 재적재
     *
     * @param cacheName 캐시 이름
     * @return 캐시 적재 결과
     */
    @Override
    public CacheLoadResult reload(String cacheName) {
        try {
            return cacheLoadService.load(cacheName);
        } catch (RuntimeException ex) {
            throw CacheException.wrap("Cache reload failed. cacheName=" + cacheName, ex);
        }
    }

    /**
     * 등록된 전체 캐시 재적재
     *
     * @return 캐시 적재 결과 목록
     */
    @Override
    public List<CacheLoadResult> reloadAll() {
        try {
            return cacheRegistry.getAll()
                .stream()
                .map(cacheLoader -> reload(cacheLoader.cacheName()))
                .toList();
        } catch (RuntimeException ex) {
            throw CacheException.wrap("Cache reload all failed.", ex);
        }
    }
}
