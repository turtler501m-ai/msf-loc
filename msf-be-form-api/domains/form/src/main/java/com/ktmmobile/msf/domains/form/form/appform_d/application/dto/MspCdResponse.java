package com.ktmmobile.msf.domains.form.form.appform_d.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MspCdResponse(
    String grpId,
    String cdVal,
    String cdDsc,
    String usgYn
) {
}
