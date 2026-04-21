package com.ktmmobile.msf.domains.shared.form.common.domain.entity;

public record TermsItem(
    String cdGroupId,
    String dtlCd,
    String dtlCdNm,
    String dtlCdAbbrNm,
    String dtlCdDesc,
    String upGrpCd,
    Integer sortOdrg,
    String expnsnStrVal1,
    String expnsnStrVal2,
    String expnsnStrVal3,
    String filePathNm
) {
}
