package com.ktmmobile.msf.domains.shared.form.common.application.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthSmsRequest(
    @NotBlank String userName,
    @NotBlank String userPhone,
    @NotBlank String type,
    String authNumber
) {
}
