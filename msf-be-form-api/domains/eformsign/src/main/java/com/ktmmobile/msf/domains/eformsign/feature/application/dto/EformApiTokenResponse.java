package com.ktmmobile.msf.domains.eformsign.feature.application.dto;

public record EformApiTokenResponse(
    String accessToken,
    String refreshToken,
    String companyId,
    String memberId
) {
}
