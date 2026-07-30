package com.ktmmobile.msf.domains.login.support.organization;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.domains.cache.agency.application.port.in.AgencyCacheReader;
import com.ktmmobile.msf.domains.cache.agency.domain.dto.AgencyCache;

@RequiredArgsConstructor
@Component
public class LoginOrganizationInfoResolver {

    private final AgencyCacheReader agencyCacheReader;

    /**
     * 대리점/판매점 코드 기준 조직 정보 조회
     *
     * @param agentCode 대리점 코드
     * @param shopCode 판매점 코드
     * @return 조직 정보
     */
    public LoginOrganizationInfo resolve(String agentCode, String shopCode) {
        return new LoginOrganizationInfo(
            agentCode,
            organizationName(agentCode),
            shopCode,
            organizationName(shopCode)
        );
    }

    /**
     * 조직 ID 기준 조직명 조회
     *
     * @param organizationId 조직 ID
     * @return 조직명
     */
    private String organizationName(String organizationId) {
        if (organizationId == null || organizationId.isBlank()) {
            return null;
        }
        return agencyCacheReader.getAgency(organizationId)
            .map(AgencyCache::organizationName)
            .orElse(null);
    }

    public record LoginOrganizationInfo(
        String agentCode,
        String agentName,
        String shopCode,
        String shopName
    ) {
    }
}
