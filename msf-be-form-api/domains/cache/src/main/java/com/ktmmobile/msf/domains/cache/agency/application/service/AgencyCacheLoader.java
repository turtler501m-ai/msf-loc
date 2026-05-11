package com.ktmmobile.msf.domains.cache.agency.application.service;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.cachecore.application.port.out.CacheLoader;
import com.ktmmobile.msf.domains.cache.agency.application.port.out.AgencyRepository;
import com.ktmmobile.msf.domains.cache.agency.domain.entity.Agency;

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
            agenciesByOrganizationId.putIfAbsent(agency.getOrganizationId(), agency);
        }
        return Map.copyOf(agenciesByOrganizationId);
    }
}
