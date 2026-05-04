package com.ktmmobile.msf.domains.form.extra.tempsave.application.dto;

import lombok.Builder;

@Builder
public record TempSavePageListResponse(
    String rowNum
) {
}
