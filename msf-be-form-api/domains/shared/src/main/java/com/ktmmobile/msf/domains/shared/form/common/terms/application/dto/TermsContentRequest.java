package com.ktmmobile.msf.domains.shared.form.common.terms.application.dto;

import com.ktmmobile.msf.domains.shared.form.common.terms.domain.entity.TermsItem;

public record TermsContentRequest(
    String groupCode,
    String contentGroupCode,
    String contentItemCode,
    String specType,
    String specCode,
    String specName
) {

    public static TermsContentRequest of(TermsItem termsItem, TermsCondition.SpecTerms specTerms) {
        if (termsItem == null) return null;

        if (specTerms == null) {
            return new TermsContentRequest(termsItem.cdGroupId(), termsItem.expnsnStrVal1(), termsItem.expnsnStrVal2(),
                null,null, null);
        }
        return new TermsContentRequest(termsItem.cdGroupId(), termsItem.expnsnStrVal1(), termsItem.expnsnStrVal2(),
            specTerms.specType(), specTerms.specCode(), specTerms.specName());
    }

    public static TermsContentRequest toEmpty() {
        return new TermsContentRequest("", "", "", null, null, null);
    }
}
