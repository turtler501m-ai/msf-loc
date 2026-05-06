package com.ktmmobile.msf.domains.shared.common.sms.application.dto;

import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CommonSmsResponse(
    @NotBlank String sendedKey,
    String authNumber
) {
    public static CommonSmsResponse of(String sendedKey) {
        return of(sendedKey, null);
    }
    public static CommonSmsResponse of(String sendedKey, String authNumber) {
        return new CommonSmsResponse(sendedKey, authNumber);
    }
}
