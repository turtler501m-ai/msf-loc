package com.ktmmobile.msf.commons.cachecore.application.port.in;

import java.util.List;

import com.ktmmobile.msf.commons.cachecore.application.dto.CacheLoadResult;

/**
 * 캐시 수동 재적재 인바운드 포트
 */
public interface CacheReloader {

    /**
     * 지정 캐시 재적재
     *
     * @param cacheName 캐시 이름
     * @return 캐시 적재 결과
     */
    CacheLoadResult reload(String cacheName);

    /**
     * 등록된 전체 캐시 재적재
     *
     * @return 캐시 적재 결과 목록
     */
    List<CacheLoadResult> reloadAll();
}
