package com.ktmmobile.msf.domains.cache.terms.domain.entity;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TermsInfo {
    String cdGroupId;
    String dtlCd;
    String dtlCdNm;
    String dtlCdAbbrNm;
    String dtlCdDesc;
    String upGrpCd;
    Integer sortOdrg;
    String expnsnStrVal1;
    String expnsnStrVal2;
    String expnsnStrVal3;
    String filePathNm;
    private String useYn;
    private String pstngStartDate;
    private String pstngEndDate;
    List<TermsDetail> details;
}
