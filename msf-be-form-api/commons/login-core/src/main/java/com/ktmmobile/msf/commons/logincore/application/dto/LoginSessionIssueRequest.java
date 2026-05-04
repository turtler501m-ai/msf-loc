package com.ktmmobile.msf.commons.logincore.application.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginSessionIssueRequest(
    @NotBlank
    String loginSessionId
) {
}
