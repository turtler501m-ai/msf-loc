package com.ktmmobile.msf.domains.eformsign.feature.adapter.client.dto;

import java.util.Base64;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformFileDownloadResponse;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record EformsignFileDownloadHttpResponse(
    String code,
    String message,
    Meta meta,
    Data data
) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Meta(
        String dataType,
        Integer dataCount
    ) {
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Data(
        String body
    ) {
    }

    public EformFileDownloadResponse toResponse(
        String documentId,
        String fileName,
        String fileCategory
    ) {

        if (!"0000".equals(code)) {
            throw new IllegalStateException(
                message != null ? message : "eformsign 파일 다운로드 실패"
            );
        }

        if (data == null || data.body() == null || data.body().isBlank()) {
            throw new IllegalStateException("eformsign PDF body가 비어 있습니다.");
        }

        byte[] fileBytes = Base64.getDecoder().decode(data.body());

        return new EformFileDownloadResponse(
            documentId,
            fileName,
            fileCategory,
            "application/pdf",
            fileBytes,
            null
        );
    }
}
