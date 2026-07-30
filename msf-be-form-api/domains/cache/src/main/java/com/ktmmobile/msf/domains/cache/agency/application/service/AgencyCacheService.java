package com.ktmmobile.msf.domains.cache.agency.application.service;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.cachecore.application.port.in.CacheReader;
import com.ktmmobile.msf.domains.cache.agency.application.port.in.AgencyCacheReader;
import com.ktmmobile.msf.domains.cache.agency.domain.dto.AgencyCache;
import com.ktmmobile.msf.domains.cache.agency.domain.dto.AgencySearchCache;
import com.ktmmobile.msf.domains.cache.agency.domain.dto.AgencySearchCaches;
import com.ktmmobile.msf.domains.cache.agency.domain.entity.Agency;

/**
 * 외부 도메인 대리점 조직 캐시 조회 기능 제공
 */
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AgencyCacheService implements AgencyCacheReader {

    private final CacheReader cacheReader;

    /** 조직ID 기준 대리점 조직 캐시 조회 */
    @Override
    public Optional<AgencyCache> getAgency(String organizationId) {
        return cacheReader.get(AgencyCacheLoader.CACHE_NAME, organizationId, Agency.class)
            .map(AgencyCache::from);
    }

    /** 조직ID 목록 기준 대리점 조직 캐시 조회 */
    @Override
    public Map<String, AgencyCache> getAgencies(Collection<String> organizationIds) {
        if (organizationIds == null || organizationIds.isEmpty()) {
            return Map.of();
        }

        Map<String, AgencyCache> agencies = new LinkedHashMap<>();
        for (String organizationId: organizationIds) {
            getAgency(organizationId).ifPresent(agency -> agencies.put(organizationId, agency));
        }
        return Collections.unmodifiableMap(agencies);
    }

    /** 조직명 기준 대리점 조직 캐시 검색 */
    @Override
    public List<AgencyCache> searchAgenciesByOrganizationName(String organizationName) {
        if (!StringUtils.hasText(organizationName)) {
            return List.of();
        }

        List<String> organizationIds = getAgencySearchCaches().searchByOrganizationName(organizationName)
            .stream()
            .map(AgencySearchCache::organizationId)
            .distinct()
            .toList();
        return List.copyOf(getAgencies(organizationIds).values());
    }

    private AgencySearchCaches getAgencySearchCaches() {
        return cacheReader.get(
                AgencyCacheLoader.SEARCH_CACHE_NAME,
                AgencySearchCaches.class
            )
            .orElse(AgencySearchCaches.empty());
    }
}
