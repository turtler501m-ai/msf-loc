package com.ktmmobile.msf.domains.form.form.appform_d.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record JuoSubInfoResponse(
    String contractNum,
    String customerSsn,
    String customerLinkName,
    String subscriberNo,
    String lstComActvDate,
    String iccId,
    String dvcChgYn,
    String gender,
    String customerId,
    String customerType,
    String legalCi,
    String lglAgntSsn
) {
}
