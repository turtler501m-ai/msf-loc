package com.ktmmobile.msf.domains.cache.commoncode.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.ktmmobile.msf.commons.common.data.type.UseYn;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeData;

/**
 * 공통코드 그룹 하위 코드 항목 응답
 */
public record CommonCodeItemResponse(
    String code,
    String title,
    UseYn useYn,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    CommonCodeResponse.DetailResponse detail
) {

    /** 공통코드 데이터의 코드 항목 응답 변환 */
    public static CommonCodeItemResponse toResponse(CommonCodeData commonCode, boolean includeDetail) {
        return new CommonCodeItemResponse(
            commonCode.code(),
            commonCode.title(),
            commonCode.useYn(),
            includeDetail ? CommonCodeResponse.DetailResponse.toResponse(commonCode.detail()) : null
        );
    }
}
