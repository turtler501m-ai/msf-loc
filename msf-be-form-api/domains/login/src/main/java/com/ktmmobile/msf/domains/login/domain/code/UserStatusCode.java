package com.ktmmobile.msf.domains.login.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnumConstant;

@Getter
@RequiredArgsConstructor
public enum UserStatusCode implements CommonEnum {

    ACTIVE("A", "활성"),
    LOCKED("C", "잠금"),

    @Deprecated
    UNDEFINED(CommonEnumConstant.UNDEFINED_CODE, "Invalid UserStatusCode");

    private final String code;
    private final String title;

    public boolean isActive() {
        return this == ACTIVE;
    }

    public static boolean isActive(String code) {
        return valueOfCode(code).isActive();
    }

    @JsonCreator
    public static UserStatusCode valueOfCode(String code) {
        return CommonEnum.valueOfCode(UserStatusCode.class, code, getInvalidValue());
    }

    @Override
    public boolean isValid() {
        return this != getInvalidValue();
    }

    public static UserStatusCode getInvalidValue() {
        return UNDEFINED;
    }
}
