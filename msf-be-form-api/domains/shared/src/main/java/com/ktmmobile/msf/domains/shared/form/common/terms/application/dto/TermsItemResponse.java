package com.ktmmobile.msf.domains.shared.form.common.terms.application.dto;

import java.util.List;
import java.util.Objects;

import lombok.With;
import lombok.extern.slf4j.Slf4j;

import com.ktmmobile.msf.domains.shared.form.common.terms.domain.entity.TermsItem;

@Slf4j
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
    public static TermsItemResponse of(TermsItem termsItem, List<TermsCondition.SpecTerms> specTerms) {
        TermsCondition.SpecTerms spec = null;
        if (specTerms != null && !specTerms.isEmpty()) {
            spec = specTerms.stream()
                .filter(s -> s.code().equals(termsItem.dtlCd())).findFirst().orElse(null);
        }

        if (spec == null) {
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
        return new TermsItemResponse(
            termsItem.cdGroupId(),
            termsItem.dtlCd(),
            spec.specName() == null ? termsItem.dtlCdNm() : termsItem.dtlCdNm().replace("#{jehuProdName}", spec.specName()),
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
