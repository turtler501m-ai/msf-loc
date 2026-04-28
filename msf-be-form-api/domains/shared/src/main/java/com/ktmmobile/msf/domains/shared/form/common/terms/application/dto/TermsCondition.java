package com.ktmmobile.msf.domains.shared.form.common.terms.application.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;

public record TermsCondition(
    String groupCode,
    List<SpecTerms> specTermsList
) {

    public record SpecTerms(
        @NotBlank String code,
        String specType,
        String specCode,
        String specName
    ) {}
}
