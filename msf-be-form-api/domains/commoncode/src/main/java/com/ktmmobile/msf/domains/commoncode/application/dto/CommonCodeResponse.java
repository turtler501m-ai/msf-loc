package com.ktmmobile.msf.domains.commoncode.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.ktmmobile.msf.commons.common.data.type.UseYn;

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
        String etcValue1,
        String etcValue2,
        String etcValue3,
        String startDate,
        String endDate
    ) {

        static DetailResponse toResponse(CommonCodeData.Detail detail) {
            if (detail == null) {
                return null;
            }

            return new DetailResponse(
                detail.abbrName(),
                detail.etcValue1(),
                detail.etcValue2(),
                detail.etcValue3(),
                detail.startDate(),
                detail.endDate()
            );
        }
    }
}
