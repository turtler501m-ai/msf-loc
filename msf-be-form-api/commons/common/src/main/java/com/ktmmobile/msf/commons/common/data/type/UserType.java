package com.ktmmobile.msf.commons.common.data.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnumConstant;

@Getter
@RequiredArgsConstructor
public enum UserType {
    FORM_USER("USER", "F", "일반 사용자"),
    ADMIN_USER("ADMIN", "A", "관리자"),

    @Deprecated
    UNDEFINED(CommonEnumConstant.UNDEFINED_CODE, "-", "Invalid User Type");


    private final String code;
    private final String simpleCode;
    private final String title;

    public boolean isFormUser() {
        return this == FORM_USER;
    }

    public boolean isAdminUser() {
        return this == ADMIN_USER;
    }

    public static UserType valueOfCode(String code) {
        for (UserType value: values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return getInvalidValue();
    }

    public static UserType valueOfSimpleCode(String simpleCode) {
        for (UserType value: values()) {
            if (value.getSimpleCode().equals(simpleCode)) {
                return value;
            }
        }
        return getInvalidValue();
    }

    public static UserType getInvalidValue() {
        return UNDEFINED;
    }

    public boolean isValid() {
        return this != getInvalidValue();
    }
}
