package com.ktmmobile.msf.domains.cache.commoncode.application.service;

import java.util.Arrays;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.cachecore.application.dto.CacheLoadResult;
import com.ktmmobile.msf.commons.cachecore.application.port.in.CacheReloader;
import com.ktmmobile.msf.domains.cache.commoncode.application.port.in.CommonCodeCacheReloader;
import com.ktmmobile.msf.domains.cache.commoncode.domain.code.CommonCodeSourceGroup;

/**
 * 공통코드 전용 재적재 요청의 cache-core 범용 재적재 포트 위임
 */
@RequiredArgsConstructor
@Service
public class CommonCodeCacheReloadService implements CommonCodeCacheReloader {

    private final CacheReloader cacheReloader;

    /** 지정 데이터소스 그룹의 공통코드 캐시 재적재 */
    @Override
    public CacheLoadResult reload(CommonCodeSourceGroup sourceGroup) {
        return cacheReloader.reload(sourceGroup.cacheName());
    }

    /** 모든 데이터소스 그룹의 공통코드 캐시 재적재 */
    @Override
    public List<CacheLoadResult> reloadAll() {
        return Arrays.stream(CommonCodeSourceGroup.values())
            .map(this::reload)
            .toList();
    }
}
