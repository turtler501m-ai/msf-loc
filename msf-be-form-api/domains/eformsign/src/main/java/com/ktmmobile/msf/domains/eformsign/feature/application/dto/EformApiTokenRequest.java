package com.ktmmobile.msf.domains.eformsign.feature.application.dto;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record EformApiTokenRequest(
    long executionTime,
    String memberId
) {
}
