package com.ktmmobile.msf.domains.eformsign.core.application.dto;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record EFormSignCoreApiTokenRequest(
    long executionTime,
    String memberId
) {
}
