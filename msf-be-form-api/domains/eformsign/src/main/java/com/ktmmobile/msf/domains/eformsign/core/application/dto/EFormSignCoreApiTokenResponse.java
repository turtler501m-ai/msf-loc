package com.ktmmobile.msf.domains.eformsign.core.application.dto;

public record EFormSignCoreApiTokenResponse(
    String accessToken,
    String refreshToken,
    String companyId,
    String memberId
) {
}
