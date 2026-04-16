package com.ktmmobile.msf.domains.commoncode.application.dto;

import com.ktmmobile.msf.domains.commoncode.domain.entity.CommonCode;

public record CommonCodeItemResponse(
    String code,
    String codeName,
    String useYn,
    CommonCodeResponse.DetailResponse detail
) {

    public static CommonCodeItemResponse of(CommonCode commonCode) {
        return new CommonCodeItemResponse(
            commonCode.getCode(),
            commonCode.getCodeName(),
            commonCode.getUseYn(),
            CommonCodeResponse.DetailResponse.of(commonCode.getDetail())
        );
    }
}
