package com.ktmmobile.msf.domains.login.application.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginPasswordChangeRequest(
    String loginSessionId,
    @NotBlank String password
) {
}
