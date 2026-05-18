package com.ktmmobile.msf.domains.cache.commoncode.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.ktmmobile.msf.commons.common.data.type.UseYn;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeData;

public record CommonCodeResponse(
    String groupId,
    String code,
    String title,
    UseYn useYn,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    DetailResponse detail
) {

    public static CommonCodeResponse toResponse(CommonCodeData commonCode, boolean includeDetail) {
        return new CommonCodeResponse(
            commonCode.groupId(),
            commonCode.code(),
            commonCode.title(),
            commonCode.useYn(),
            includeDetail ? DetailResponse.toResponse(commonCode.detail()) : null
        );
    }

    public record DetailResponse(
        String abbrName,
        String description,
        String upperGroupCode,
        String filePathName,
        String imageName,
        int sortOrder,
        String etcValue1,
        String etcValue2,
        String etcValue3,
        String etcValue4,
        String etcValue5,
        String etcValue6,
        String startDate,
        String endDate
    ) {

        static DetailResponse toResponse(CommonCodeData.Detail detail) {
            if (detail == null) {
                return empty();
            }

            return new DetailResponse(
                detail.abbrName(),
                detail.description(),
                detail.upperGroupCode(),
                detail.filePathName(),
                detail.imageName(),
                detail.sortOrder(),
                detail.etcValue1(),
                detail.etcValue2(),
                detail.etcValue3(),
                detail.etcValue4(),
                detail.etcValue5(),
                detail.etcValue6(),
                detail.startDate(),
                detail.endDate()
            );
        }

        private static DetailResponse empty() {
            return new DetailResponse(
                null,
                null,
                null,
                null,
                null,
                0,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            );
        }
    }
}
