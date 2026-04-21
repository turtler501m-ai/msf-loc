package com.ktmmobile.msf.domains.shared.form.common.application.dto;

import java.util.List;

import lombok.With;

import com.ktmmobile.msf.domains.shared.form.common.domain.entity.TermsGroup;

public record TermsGroupResponse(
    String code,
    String name,
    String groupDescription,
    String value1,
    String value2,
    String value3,
    @With
    List<TermsItemResponse> codes
) {
    public static TermsGroupResponse of(TermsGroup termsGroup) {
        return new TermsGroupResponse(termsGroup.cdGroupId(),
            termsGroup.cdGroupNm(),
            termsGroup.cdGroupDesc(),
            termsGroup.expnsnStrVal1(),
            termsGroup.expnsnStrVal2(),
            termsGroup.expnsnStrVal3(),
            List.of()
        );
    }
}
