package com.ktmmobile.msf.domains.cache.terms.domain.entity;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 약관 그룹 캐시 원천 데이터
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TermsType {
    private String cdGroupId;
    private String cdGroupNm;
    private String cdGroupDesc;
    private String cdGroupCd;
    private String expnsnStrVal1;
    private String expnsnStrVal2;
    private String expnsnStrVal3;
    private String useYn;
    private String pstngStartDate;
    private String pstngEndDate;
    private List<TermsInfo> infos;
}
