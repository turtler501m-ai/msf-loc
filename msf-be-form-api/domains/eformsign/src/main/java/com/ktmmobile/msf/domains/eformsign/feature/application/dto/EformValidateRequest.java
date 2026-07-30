package com.ktmmobile.msf.domains.eformsign.feature.application.dto;

import java.util.List;

public record EformValidateRequest(
    String accessToken,
    String documentId,
    List<DocumentComponent> componentIds
) {

    public record DocumentComponent(
        String name,
        String value
    ) {
    }
}
