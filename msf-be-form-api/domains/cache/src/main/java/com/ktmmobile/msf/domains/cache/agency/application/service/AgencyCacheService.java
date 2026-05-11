package com.ktmmobile.msf.domains.cache.agency.application.service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.cachecore.application.port.in.CacheReader;
import com.ktmmobile.msf.domains.cache.agency.application.port.in.AgencyCacheReader;
import com.ktmmobile.msf.domains.cache.agency.domain.dto.AgencyCache;
import com.ktmmobile.msf.domains.cache.agency.domain.entity.Agency;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AgencyCacheService implements AgencyCacheReader {

    private final CacheReader cacheReader;

    @Override
    public Optional<AgencyCache> getAgency(String organizationId) {
        return cacheReader.get(AgencyCacheLoader.CACHE_NAME, organizationId, Agency.class)
            .map(AgencyCache::from);
    }

    @Override
    public Map<String, AgencyCache> getAgencies(List<String> organizationIds) {
        if (organizationIds == null || organizationIds.isEmpty()) {
            return Map.of();
        }

        Map<String, AgencyCache> agencies = new LinkedHashMap<>();
        for (String organizationId: organizationIds) {
            getAgency(organizationId).ifPresent(agency -> agencies.put(organizationId, agency));
        }
        return Collections.unmodifiableMap(agencies);
    }
}
