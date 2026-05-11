package com.ktmmobile.msf.domains.cache.agency.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import com.ktmmobile.msf.domains.policy.organization.OrganizationType;

@Getter
@NoArgsConstructor
public class Agency {

    private String ktOrganizationId;        // KT 조직ID
    private String organizationId;          // 조직ID
    private String organizationName;        // 조직명
    private String typeCode;                // 유형코드
    private String levelCode;               // 조직레벨코드
    private String higherOrganizationCode;  // 상위조직코드

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
