package com.ktmmobile.msf.domains.cache.agency.domain.dto;

import java.util.Collection;
import java.util.List;

import com.ktmmobile.msf.domains.cache.agency.domain.entity.Agency;

/**
 * 대리점 조직명 검색 캐시 목록
 */
public record AgencySearchCaches(
    List<AgencySearchCache> agencies
) {

    private static final AgencySearchCaches EMPTY = new AgencySearchCaches(List.of());

    public AgencySearchCaches {
        agencies = agencies == null ? List.of() : List.copyOf(agencies);
    }

    /** 대리점 조직 엔터티 목록을 검색 캐시 목록으로 변환 */
    public static AgencySearchCaches from(Collection<Agency> agencies) {
        if (agencies == null || agencies.isEmpty()) {
            return empty();
        }
        return new AgencySearchCaches(agencies.stream()
            .map(AgencySearchCache::from)
            .toList());
    }

    /** 빈 대리점 조직명 검색 캐시 목록 반환 */
    public static AgencySearchCaches empty() {
        return EMPTY;
    }

    /** 조직명 기준 검색 */
    public List<AgencySearchCache> searchByOrganizationName(String organizationName) {
        return agencies.stream()
            .filter(agency -> agency.containsOrganizationName(organizationName))
            .toList();
    }
}
