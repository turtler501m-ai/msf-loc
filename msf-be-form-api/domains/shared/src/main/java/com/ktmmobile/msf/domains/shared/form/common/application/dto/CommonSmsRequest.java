package com.ktmmobile.msf.domains.shared.form.common.application.dto;

import jakarta.validation.constraints.NotBlank;

public record CommonSmsRequest(
    @NotBlank String type,
    @NotBlank String path,
    String token,
    String phone,
    String value
) {
}
