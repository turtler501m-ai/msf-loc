package com.ktmmobile.msf.domains.eformsign.feature.adapter.client.dto;

import java.util.List;

public record EformValidateHttpResponse(
    String status,
    String code,
    String message,
    Result result
) {

    public record Result(
        List<DocumentComponent> documentComponentList
    ) { }

    public record DocumentComponent(
        String name,
        String value
    ) { }
}