package com.ktmmobile.msf.domains.eformsign.core.adapter.client.dto;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import com.ktmmobile.msf.domains.eformsign.core.application.dto.EFormSignCoreApiTokenResponse;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record EFormSignCoreApiTokenHttpResponse(
    ApiKey apiKey,
    OauthToken oauthToken
) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record ApiKey(
        String name,
        String alias,
        Company company
    ) {
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Company(
        String companyId,
        String name,
        String apiUrl
    ) {
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record OauthToken(
        long expiresIn,
        String tokenType,
        String refreshToken,
        String accessToken
    ) {
    }

    public EFormSignCoreApiTokenResponse toResponse(String memberId) {
        return new EFormSignCoreApiTokenResponse(oauthToken().accessToken(), oauthToken().refreshToken(), apiKey().company().companyId(), memberId);
    }
}
