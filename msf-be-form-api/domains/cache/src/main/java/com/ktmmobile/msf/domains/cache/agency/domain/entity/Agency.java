package com.ktmmobile.msf.domains.cache.agency.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import com.ktmmobile.msf.domains.policy.organization.OrganizationType;

/**
 * MSP 조직 정보에서 조회한 대리점 조직 캐시 원천 데이터
 */
@Getter
@NoArgsConstructor
public class Agency {

    private String ktOrganizationId;        // KT 조직ID
    private String organizationId;          // 조직ID
    private String organizationName;        // 조직명
    private String typeCode;                // 유형코드
    private String levelCode;               // 조직레벨코드
    private String higherOrganizationCode;  // 상위조직코드
    private String respnPrsnId;             // 담당자ID
    private String respnPrsnNm;             // 담당자명
    private String admUserId;               // 관리자ID
    private String telephone;               // 전화번호
    private String representativeTelephone; // 대표전화번호

    /** 대리점 조직 여부 반환 */
    public boolean isAgency() {
        return OrganizationType.isAgency(this.typeCode, this.levelCode);
    }

    /** 판매점 조직 여부 반환 */
    public boolean isSalesAgency() {
        return OrganizationType.isSalesAgency(this.typeCode, this.levelCode);
    }

    /** 본사 조직 여부 반환 */
    public boolean isHeadOffice() {
        return OrganizationType.isHeadOffice(this.typeCode);
    }
}
