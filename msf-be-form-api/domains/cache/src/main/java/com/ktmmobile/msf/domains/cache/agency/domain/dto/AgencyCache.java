package com.ktmmobile.msf.domains.cache.agency.domain.dto;

import com.ktmmobile.msf.domains.cache.agency.domain.entity.Agency;
import com.ktmmobile.msf.domains.policy.organization.OrganizationType;

/**
 * 외부 도메인에서 사용하는 대리점 조직 캐시 데이터
 */
public record AgencyCache(
    String ktOrganizationId,       // KT 조직ID
    String organizationId,         // 조직ID
    String organizationName,       // 조직명
    String typeCode,               // 유형코드
    String levelCode,              // 조직레벨코드
    String higherOrganizationCode, // 상위조직코드
    String respnPrsnId,            // 담당자ID
    String respnPrsnNm,            // 담당자명
    String admUserId,              // 관리자ID
    String telephone,              // 전화번호
    String representativeTelephone // 대표전화번호
) {

    private static final AgencyCache EMPTY = new AgencyCache("", "", "", "", "", "", "", "", "", "", "");

    /** 대리점 조직 엔터티를 캐시 조회 DTO로 변환 */
    public static AgencyCache from(Agency agency) {
        return new AgencyCache(
            agency.getKtOrganizationId(),
            agency.getOrganizationId(),
            agency.getOrganizationName(),
            agency.getTypeCode(),
            agency.getLevelCode(),
            agency.getHigherOrganizationCode(),
            agency.getRespnPrsnId(),
            agency.getRespnPrsnNm(),
            agency.getAdmUserId(),
            agency.getTelephone(),
            agency.getRepresentativeTelephone()
        );
    }

    /** 조직 정보가 없을 때 사용할 빈 대리점 조직 캐시 데이터 반환 */
    public static AgencyCache empty() {
        return EMPTY;
    }

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
