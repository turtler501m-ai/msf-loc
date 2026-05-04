package com.ktmmobile.msf.domains.form.extra.receipt.application.dto;

import lombok.Builder;

@Builder
public record ReceiptPageListResponse(
    String rowNum
) {
}
