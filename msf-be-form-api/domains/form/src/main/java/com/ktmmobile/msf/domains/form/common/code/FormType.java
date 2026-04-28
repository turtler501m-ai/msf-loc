package com.ktmmobile.msf.domains.form.common.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnumConstant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FormType implements CommonEnum {
    NEWCHANGE("1", "신규/변경"),
    SERVICECHANGE("2", "서비스변경"),
    OWNERCHANGE("3", "명의변경"),
    TERMINATION("4", "서비스해지"),

    @Deprecated
    UNDEFINED(CommonEnumConstant.UNDEFINED_CODE, "Invalid FormType");

    private final String code;
    private final String title;

    public boolean isNewChange() {
        return this == NEWCHANGE;
    }

    public boolean isServiceChange() {
        return this == SERVICECHANGE;
    }

    @Override
    public boolean isValid() {
        return this != getInvalidValue();
    }

    public static FormType getInvalidValue() {
        return UNDEFINED;
    }

    @JsonCreator
    public static FormType valueOfCode(String code) {
        return CommonEnum.valueOfCode(FormType.class, code, getInvalidValue());
    }
}
