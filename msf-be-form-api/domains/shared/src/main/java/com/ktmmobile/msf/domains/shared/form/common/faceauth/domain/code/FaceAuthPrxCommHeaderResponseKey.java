package com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum FaceAuthPrxCommHeaderResponseKey implements CommonEnum {
    GLOBAL_NO("globalNo", "Global No"),
    RESPONSE_TYPE("responseType", "응답유형"),
    RESPONSE_CODE("responseCode", "에러상세코드"),
    RESPONSE_MESSAGE("responseBasic", "에러메시지");

    private final String code;
    private final String title;
}
