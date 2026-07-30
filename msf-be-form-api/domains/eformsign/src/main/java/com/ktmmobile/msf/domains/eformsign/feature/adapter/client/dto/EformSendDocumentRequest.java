package com.ktmmobile.msf.domains.eformsign.feature.adapter.client.dto;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record EformSendDocumentRequest(
    String authPwd,
    String authHint
) { }
