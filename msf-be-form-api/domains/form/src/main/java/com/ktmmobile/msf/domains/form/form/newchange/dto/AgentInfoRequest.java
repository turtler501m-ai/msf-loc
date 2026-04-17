package com.ktmmobile.msf.domains.form.form.newchange.dto;

import jakarta.validation.constraints.NotBlank;

public record AgentInfoRequest(
    @NotBlank String cntpntCd
) {
}
