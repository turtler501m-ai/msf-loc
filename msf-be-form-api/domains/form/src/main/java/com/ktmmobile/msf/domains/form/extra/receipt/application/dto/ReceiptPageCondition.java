package com.ktmmobile.msf.domains.form.extra.receipt.application.dto;

import lombok.With;

import com.ktmmobile.msf.commons.common.pagination.PageCondition;

public record ReceiptPageCondition(
    String searchWord,

    @With PageCondition page
) {
}
