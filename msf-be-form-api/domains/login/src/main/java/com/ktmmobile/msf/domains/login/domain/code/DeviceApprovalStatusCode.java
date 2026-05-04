package com.ktmmobile.msf.domains.login.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnumConstant;

@Getter
@RequiredArgsConstructor
public enum DeviceApprovalStatusCode implements CommonEnum {

    APPROVED("A", "승인"),

    @Deprecated
    UNDEFINED(CommonEnumConstant.UNDEFINED_CODE, "Invalid DeviceApprovalStatusCode");

    private final String code;
    private final String title;

    public boolean isApproved() {
        return this == APPROVED;
    }

    public static boolean isApproved(String code) {
        return valueOfCode(code).isApproved();
    }

    @JsonCreator
    public static DeviceApprovalStatusCode valueOfCode(String code) {
        return CommonEnum.valueOfCode(DeviceApprovalStatusCode.class, code, getInvalidValue());
    }

    @Override
    public boolean isValid() {
        return this != getInvalidValue();
    }

    public static DeviceApprovalStatusCode getInvalidValue() {
        return UNDEFINED;
    }
}
