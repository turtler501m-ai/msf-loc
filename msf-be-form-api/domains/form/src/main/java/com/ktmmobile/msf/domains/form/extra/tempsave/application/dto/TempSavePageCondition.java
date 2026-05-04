package com.ktmmobile.msf.domains.form.extra.tempsave.application.dto;

import lombok.With;

import com.ktmmobile.msf.commons.common.pagination.PageCondition;

public record TempSavePageCondition(
    String searchWord,

    @With PageCondition page
) {
}
