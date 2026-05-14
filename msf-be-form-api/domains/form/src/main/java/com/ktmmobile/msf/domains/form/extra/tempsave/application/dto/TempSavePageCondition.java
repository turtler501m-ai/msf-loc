package com.ktmmobile.msf.domains.form.extra.tempsave.application.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.With;

import com.ktmmobile.msf.commons.common.pagination.PageCondition;

@Builder(toBuilder = true)
public record TempSavePageCondition(
    LocalDate startDt,
    LocalDate endDt,
    List<String> formTypeCd,
    List<String> procCd,
    Integer requestKey,
    String cstmrNm,
    String cstmrNativeBirth,
    String shopCd,
    String shopNm,
    String searchWord,
    String authAgentCd,
    String authShopCd,
    String authCretId,
    @With PageCondition page
) {
}
