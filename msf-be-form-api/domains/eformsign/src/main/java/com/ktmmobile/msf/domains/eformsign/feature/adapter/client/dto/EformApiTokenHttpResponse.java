package com.ktmmobile.msf.domains.eformsign.feature.adapter.client.dto;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformApiTokenResponse;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record EformApiTokenHttpResponse(
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

    public EformApiTokenResponse toResponse(String memberId) {
        return new EformApiTokenResponse(oauthToken().accessToken(), oauthToken().refreshToken(), apiKey().company().companyId(), memberId);
    }
}
