package com.ktmmobile.msf.domains.cache.agency.domain.dto;

import com.ktmmobile.msf.domains.cache.agency.domain.entity.Agency;
import com.ktmmobile.msf.domains.policy.organization.OrganizationType;

public record AgencyCache(
    String ktOrganizationId,       // KT 조직ID
    String organizationId,         // 조직ID
    String organizationName,       // 조직명
    String typeCode,               // 유형코드
    String levelCode,              // 조직레벨코드
    String higherOrganizationCode  // 상위조직코드
) {

    public static AgencyCache from(Agency agency) {
        return new AgencyCache(
            agency.getKtOrganizationId(),
            agency.getOrganizationId(),
            agency.getOrganizationName(),
            agency.getTypeCode(),
            agency.getLevelCode(),
            agency.getHigherOrganizationCode()
        );
    }

    public boolean isAgency() {
        return OrganizationType.isAgency(this.typeCode, this.levelCode);
    }

    public boolean isSalesAgency() {
        return OrganizationType.isSalesAgency(this.typeCode, this.levelCode);
    }

    public boolean isHeadOffice() {
        return OrganizationType.isHeadOffice(this.typeCode);
    }
}
