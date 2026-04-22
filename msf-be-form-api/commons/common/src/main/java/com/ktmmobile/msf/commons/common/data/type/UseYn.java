package com.ktmmobile.msf.commons.common.data.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnumConstant;

@Getter
@RequiredArgsConstructor
public enum UseYn implements CommonEnum {
    YES("Y", "사용"),
    NO("N", "미사용"),

    @Deprecated
    UNDEFINED(CommonEnumConstant.UNDEFINED_CODE, "Invalid UseYn");

    private final String code;
    private final String title;

    public boolean isUsed() {
        return this == YES;
    }

    public boolean isUnused() {
        return this == NO;
    }

    @Override
    public boolean isValid() {
        return this != UNDEFINED;
    }

    @JsonCreator
    public static UseYn valueOfCode(String code) {
        return CommonEnum.valueOfCode(UseYn.class, code, UNDEFINED);
    }
}
