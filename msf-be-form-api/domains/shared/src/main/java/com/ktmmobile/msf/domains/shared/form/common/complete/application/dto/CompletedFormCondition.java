package com.ktmmobile.msf.domains.shared.form.common.complete.application.dto;

import jakarta.validation.constraints.NotBlank;

public record CompletedFormCondition(
    @NotBlank String requestKey
) {
}
