package com.ktmmobile.msf.domains.cache.agency.application.service;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.cachecore.application.port.out.CacheExtensionLoader;
import com.ktmmobile.msf.commons.cachecore.application.port.out.CacheLoader;
import com.ktmmobile.msf.commons.cachecore.domain.code.CacheStoreType;
import com.ktmmobile.msf.domains.cache.agency.application.port.out.AgencyRepository;
import com.ktmmobile.msf.domains.cache.agency.domain.dto.AgencySearchCaches;
import com.ktmmobile.msf.domains.cache.agency.domain.entity.Agency;

/**
 * 활성 조직 정보 organizationId 기준 Hash 캐시 적재
 */
@RequiredArgsConstructor
@Component
public class AgencyCacheLoader implements CacheLoader<Agency> {

    public static final String CACHE_NAME = "agencies";
    static final String SEARCH_CACHE_NAME = "agency-searches";

    private final AgencyRepository agencyRepository;

    /** 대리점 조직 캐시 이름 반환 */
    @Override
    public String cacheName() {
        return CACHE_NAME;
    }

    /** 사용 중인 조직 정보 organizationId 기준 적재 */
    @Override
    public Map<String, Agency> load() {
        Map<String, Agency> agenciesByOrganizationId = new LinkedHashMap<>();
        for (Agency agency: agencyRepository.findAllActiveAgencies()) {
            // 정렬된 원천 조회 결과 기준 동일 organizationId 최신 항목 유지
            agenciesByOrganizationId.putIfAbsent(agency.getOrganizationId(), agency);
        }
        return Map.copyOf(agenciesByOrganizationId);
    }

    /** 대리점 조직 캐시 확장 로더 목록 반환 */
    @Override
    public Collection<CacheExtensionLoader<Agency, ?>> extensionLoaders() {
        return List.of(new AgencySearchExtensionLoader());
    }


    private static class AgencySearchExtensionLoader implements CacheExtensionLoader<Agency, AgencySearchCaches> {

        /** 대리점 조직명 검색 캐시 이름 반환 */
        @Override
        public String cacheName() {
            return SEARCH_CACHE_NAME;
        }

        /** 단일 Value 저장 방식 반환 */
        @Override
        public CacheStoreType storeType() {
            return CacheStoreType.SINGLE_VALUE;
        }

        /** 대리점 조직 캐시 기반 조직명 검색 캐시 적재 */
        @Override
        public Map<String, AgencySearchCaches> load(Map<String, Agency> sourceValues) {
            return Map.of(SEARCH_CACHE_NAME, AgencySearchCaches.from(sourceValues.values()));
        }
    }
}
