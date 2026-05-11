package com.ktmmobile.msf.domains.login.support.organization;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.domains.cache.agency.application.port.in.AgencyCacheReader;
import com.ktmmobile.msf.domains.cache.agency.domain.dto.AgencyCache;

@RequiredArgsConstructor
@Component
public class LoginOrganizationInfoResolver {

    private final AgencyCacheReader agencyCacheReader;

    public LoginOrganizationInfo resolve(String agentCode, String shopCode) {
        return new LoginOrganizationInfo(
            agentCode,
            organizationName(agentCode),
            shopCode,
            organizationName(shopCode)
        );
    }

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
