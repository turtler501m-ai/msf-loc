package com.ktmmobile.msf.domains.eformsign.application.dto;

public record EformApiTokenResponse(
    String accessToken,
    String refreshToken
) {
}
