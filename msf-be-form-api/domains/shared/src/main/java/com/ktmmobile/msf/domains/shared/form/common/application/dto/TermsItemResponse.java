package com.ktmmobile.msf.domains.shared.form.common.application.dto;

import java.util.Objects;

import lombok.With;

import com.ktmmobile.msf.domains.shared.form.common.domain.entity.TermsItem;

public record TermsItemResponse(
    String groupCode,
    String code,
    String name,
    String abbreviation,
    Boolean commonStatus,
    String parentCode,
    Integer sortNumber,
    String termsGroupCd,
    String termsItemCd,
    String required,
    String path,
    @With
    String version,
    @With
    String content
) {
    public static TermsItemResponse of(TermsItem termsItem) {
        return new TermsItemResponse(
            termsItem.cdGroupId(),
            termsItem.dtlCd(),
            termsItem.dtlCdNm(),
            termsItem.dtlCdAbbrNm(),
            Objects.equals(termsItem.dtlCdDesc(), "Y"),
            termsItem.upGrpCd(),
            termsItem.sortOdrg(),
            termsItem.expnsnStrVal1(),
            termsItem.expnsnStrVal2(),
            termsItem.expnsnStrVal3(),
            termsItem.filePathNm(),
            null,
            null
        );
    }
}
