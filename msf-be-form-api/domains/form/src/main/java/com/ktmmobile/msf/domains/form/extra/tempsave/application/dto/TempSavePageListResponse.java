package com.ktmmobile.msf.domains.form.extra.tempsave.application.dto;

public record TempSavePageListResponse(
    String rowNum,
    String requestKey,
    String cretDt,
    String openTypeNm,
    String serviceTypeNm,
    String cstmrTypeNm,
    String modifyYn,
    String cstmrNm
) {
}
