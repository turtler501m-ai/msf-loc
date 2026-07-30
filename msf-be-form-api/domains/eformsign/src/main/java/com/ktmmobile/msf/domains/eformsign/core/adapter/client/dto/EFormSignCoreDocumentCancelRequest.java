package com.ktmmobile.msf.domains.eformsign.core.adapter.client.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EFormSignCoreDocumentCancelRequest(
    Input input
) {

    public static EFormSignCoreDocumentCancelRequest of(List<String> documentIds) {
        return new EFormSignCoreDocumentCancelRequest(
            new Input(
                documentIds,
                String.join(", ", documentIds)
            )
        );
    }

    public record Input(
        @JsonProperty("document_ids")
        List<String> documentIds,
        String comment
    ) {
    }
}
