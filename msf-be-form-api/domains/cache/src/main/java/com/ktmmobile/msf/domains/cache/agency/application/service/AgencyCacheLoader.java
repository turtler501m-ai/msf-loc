package com.ktmmobile.msf.domains.cache.agency.application.service;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.cachecore.application.port.out.CacheLoader;
import com.ktmmobile.msf.domains.cache.agency.application.port.out.AgencyRepository;
import com.ktmmobile.msf.domains.cache.agency.domain.entity.Agency;

/**
 * 활성 조직 정보를 organizationId 기준 Hash 캐시로 적재한다.
 */
@RequiredArgsConstructor
@Component
public class AgencyCacheLoader implements CacheLoader<Agency> {

    static final String CACHE_NAME = "agencies";

    private final AgencyRepository agencyRepository;

    @Override
    public String cacheName() {
        return CACHE_NAME;
    }

    @Override
    public Map<String, Agency> load() {
        Map<String, Agency> agenciesByOrganizationId = new LinkedHashMap<>();
        for (Agency agency: agencyRepository.findAllActiveAgencies()) {
            // 정렬된 원천 조회 결과에서 같은 organizationId가 있으면 최신 항목을 유지한다.
            agenciesByOrganizationId.putIfAbsent(agency.getOrganizationId(), agency);
        }
        return Map.copyOf(agenciesByOrganizationId);
    }
}
