package com.ktmmobile.msf.domains.eformsign.feature.adapter.client.dto;

import java.util.List;

import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformValidateRequest;

public record EformValidateHttpRequest(
    List<DocumentComponentBody> documentComponentBodyList
) {

    public record DocumentComponentBody(
        String name,
        String value
    ) {
    }

    public static EformValidateHttpRequest of(List<EformValidateRequest.DocumentComponent> components) {
        return new EformValidateHttpRequest(
            components.stream()
                .map(component -> new DocumentComponentBody(
                    component.name(),
                    component.value()
                ))
                .toList()
        );
    }
}
