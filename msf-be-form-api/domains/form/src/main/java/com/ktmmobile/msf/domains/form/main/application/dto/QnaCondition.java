package com.ktmmobile.msf.domains.form.main.application.dto;

import lombok.Builder;
import lombok.With;

import com.ktmmobile.msf.commons.common.pagination.PageCondition;

@Builder(toBuilder = true)
public record QnaCondition(
    String category,
    String value,
    String startDate,
    String endDate,
    @With String loginId,
    @With PageCondition page
) {
}
