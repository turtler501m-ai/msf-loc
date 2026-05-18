package com.ktmmobile.msf.domains.cache.agency.application.port.in;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ktmmobile.msf.domains.cache.agency.domain.dto.AgencyCache;

/**
 * 대리점 조직 정보를 캐시에서 조회하는 인바운드 포트
 */
public interface AgencyCacheReader {

    /** 조직ID 기준 단건 조회 */
    Optional<AgencyCache> getAgency(String organizationId);

    /** 조직ID 기준 다건 조회 */
    Map<String, AgencyCache> getAgencies(List<String> organizationIds);
}
