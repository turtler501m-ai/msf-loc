package com.ktmmobile.msf.domains.shared.common.sms.application.dto;

import jakarta.validation.constraints.NotBlank;

import com.ktmmobile.msf.domains.shared.common.sms.domain.code.CommonSmsType;

public record CommonSmsRequest(
    CommonSmsType type,
    @NotBlank String path,
    String token,
    String name,
    String phone,
    String value,
    String title,
    String message,
    String url
) {
}
