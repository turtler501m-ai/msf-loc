package com.ktmmobile.msf.domains.form.common.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnumConstant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryType implements CommonEnum {
    PRICE("P", "Price"),
    RATE_ADDITION("R", "RateAddition"),
    INSURANCE("I", "Insurance"),

    @Deprecated
    UNDEFINED(CommonEnumConstant.UNDEFINED_CODE, "Invalid CategoryType");

    private final String code;
    private final String title;

    @Override
    public boolean isValid() {
        return this != getInvalidValue();
    }

    public static CategoryType getInvalidValue() {
        return UNDEFINED;
    }

    @JsonCreator
    public static CategoryType valueOfCode(String code) {
        return CommonEnum.valueOfCode(CategoryType.class, code, getInvalidValue());
    }
}
