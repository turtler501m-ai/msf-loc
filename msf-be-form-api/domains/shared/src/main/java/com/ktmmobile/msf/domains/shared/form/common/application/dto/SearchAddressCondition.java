package com.ktmmobile.msf.domains.shared.form.common.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record SearchAddressCondition(
    @Min(1) Integer currentPage,
    @Min(1) Integer countPerPage,
    @NotBlank String keyword
) {
}
