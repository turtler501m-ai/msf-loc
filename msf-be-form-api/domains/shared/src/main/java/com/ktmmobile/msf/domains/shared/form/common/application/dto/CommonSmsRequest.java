package com.ktmmobile.msf.domains.shared.form.common.application.dto;

import jakarta.validation.constraints.NotBlank;

public record CommonSmsRequest(
    @NotBlank String userId,
    @NotBlank String phone1,
    @NotBlank String phone2,
    @NotBlank String phone3,
    @NotBlank String type,
    String authNumber
) {
}
