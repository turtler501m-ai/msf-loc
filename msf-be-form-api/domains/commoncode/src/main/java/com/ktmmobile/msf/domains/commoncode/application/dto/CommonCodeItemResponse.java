package com.ktmmobile.msf.domains.commoncode.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.ktmmobile.msf.commons.common.data.type.UseYn;

public record CommonCodeItemResponse(
    String code,
    String title,
    UseYn useYn,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    CommonCodeResponse.DetailResponse detail
) {

    public static CommonCodeItemResponse toResponse(CommonCodeData commonCode, boolean includeDetail) {
        return new CommonCodeItemResponse(
            commonCode.code(),
            commonCode.title(),
            commonCode.useYn(),
            includeDetail ? CommonCodeResponse.DetailResponse.toResponse(commonCode.detail()) : null
        );
    }
}
