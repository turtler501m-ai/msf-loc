package com.ktmmobile.msf.domains.cache.commoncode.application.service;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.cachecore.application.port.out.CacheLoader;
import com.ktmmobile.msf.domains.cache.commoncode.application.port.out.CommonCodeRepository;
import com.ktmmobile.msf.domains.cache.commoncode.domain.code.CommonCodeSourceGroup;
import com.ktmmobile.msf.domains.cache.commoncode.domain.entity.CommonCode;

/**
 * MSP 데이터소스의 공통코드만 적재하는 물리 캐시 로더
 */
@RequiredArgsConstructor
@Component
public class MspCommonCodeCacheLoader implements CacheLoader<List<CommonCode>> {

    private final CommonCodeRepository commonCodeRepository;

    @Override
    public String cacheName() {
        return CommonCodeSourceGroup.MSP.cacheName();
    }

    @Override
    public Map<String, List<CommonCode>> load() {
        return CommonCodeCacheValues.groupByGroupId(commonCodeRepository.findMspCommonCodes());
    }
}
