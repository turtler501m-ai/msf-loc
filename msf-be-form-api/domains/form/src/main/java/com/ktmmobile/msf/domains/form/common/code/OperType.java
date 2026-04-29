package com.ktmmobile.msf.domains.form.common.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnumConstant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OperType implements CommonEnum {
    NEW_ACTIVATION("NAC3", "신규가입"),
    MOBILE_NUMBER_PORTABILITY("MNP3", "번호이동"),
    HANDSET_CHANGE("HDN3", "기기변경"),

    @Deprecated
    UNDEFINED(CommonEnumConstant.UNDEFINED_CODE, "Invalid OperType");

    private final String code;
    private final String title;

    @Override
    public boolean isValid() {
        return this != getInvalidValue();
    }

    public static OperType getInvalidValue() {
        return UNDEFINED;
    }

    @JsonCreator
    public static OperType valueOfCode(String code) {
        return CommonEnum.valueOfCode(OperType.class, code, getInvalidValue());
    }
}
