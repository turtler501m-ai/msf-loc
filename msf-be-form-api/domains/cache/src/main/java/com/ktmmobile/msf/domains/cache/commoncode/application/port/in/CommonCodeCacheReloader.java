package com.ktmmobile.msf.domains.cache.commoncode.application.port.in;

import java.util.List;

import com.ktmmobile.msf.commons.cachecore.application.dto.CacheLoadResult;
import com.ktmmobile.msf.domains.cache.commoncode.domain.code.CommonCodeSourceGroup;

/**
 * 공통코드 캐시를 데이터소스 그룹 단위로 재적재하는 인바운드 포트
 */
public interface CommonCodeCacheReloader {

    /** 지정 데이터소스 그룹의 공통코드 캐시 재적재 */
    CacheLoadResult reload(CommonCodeSourceGroup sourceGroup);

    /** 모든 데이터소스 그룹의 공통코드 캐시 재적재 */
    List<CacheLoadResult> reloadAll();
}
