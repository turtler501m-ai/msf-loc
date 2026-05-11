package com.ktmmobile.msf.domains.cache.agency.application.port.in;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ktmmobile.msf.domains.cache.agency.domain.dto.AgencyCache;

public interface AgencyCacheReader {

    Optional<AgencyCache> getAgency(String organizationId);

    Map<String, AgencyCache> getAgencies(List<String> organizationIds);
}
