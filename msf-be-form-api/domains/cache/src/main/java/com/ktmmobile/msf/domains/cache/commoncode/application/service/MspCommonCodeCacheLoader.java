package com.ktmmobile.msf.domains.cache.commoncode.application.service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    /** MSP 공통코드 캐시 이름 반환 */
    @Override
    public String cacheName() {
        return CommonCodeSourceGroup.MSP.cacheName();
    }

    /** MSP 공통코드 목록 groupId 기준 적재 */
    @Override
    public Map<String, List<CommonCode>> load() {
        return CommonCodeCacheValues.groupByGroupId(commonCodeRepository.findMspCommonCodes());
    }

    /** 애플리케이션 시작 시 공통코드 강제 재적재 기준 시간 반환 */
    @Override
    public Optional<Duration> startupReloadAfter() {
        return Optional.of(Duration.ofMinutes(10));
    }
}
