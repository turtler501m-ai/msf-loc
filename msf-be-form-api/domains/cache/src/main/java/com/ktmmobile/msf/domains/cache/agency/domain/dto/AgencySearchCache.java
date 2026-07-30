package com.ktmmobile.msf.domains.cache.agency.domain.dto;

import java.util.Locale;

import org.springframework.util.StringUtils;

import com.ktmmobile.msf.domains.cache.agency.domain.entity.Agency;

/**
 * 대리점 조직명 검색 캐시 데이터
 */
public record AgencySearchCache(
    String organizationId,
    String organizationName
) {

    public AgencySearchCache {
        organizationId = defaultString(organizationId);
        organizationName = defaultString(organizationName);
    }

    /** 대리점 조직 엔터티를 검색 캐시 데이터로 변환 */
    public static AgencySearchCache from(Agency agency) {
        return new AgencySearchCache(
            agency.getOrganizationId(),
            agency.getOrganizationName()
        );
    }

    /** 조직명 검색어 포함 여부 반환 */
    public boolean containsOrganizationName(String keyword) {
        String normalizedKeyword = normalizeOrganizationName(keyword);
        return StringUtils.hasText(normalizedKeyword)
            && normalizeOrganizationName(organizationName).contains(normalizedKeyword);
    }

    /** 조직명 검색값 정규화 */
    public static String normalizeOrganizationName(String value) {
        if (!StringUtils.hasText(value)) {
            return "";
        }
        return value.toLowerCase(Locale.ROOT)
            .replaceAll("\\s+", "");
    }

    private static String defaultString(String value) {
        return value == null ? "" : value;
    }
}
