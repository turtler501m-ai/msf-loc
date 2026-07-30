package com.ktmmobile.msf.domains.shared.form.common.complete.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnumConstant;

@Getter
@RequiredArgsConstructor
public enum RequestFormOperType implements CommonEnum {
    NEW("NAC3", "신규개통"),
    MOVE("MNP3", "번호이동"),
    CHANGE("HDN3", "기기변경"),

    @Deprecated
    UNDEFINED(CommonEnumConstant.UNDEFINED_CODE, "Invalid OperType");

    private final String code;
    private final String title;

    @Override
    public boolean isValid() {
        return this != getInvalidValue();
    }

    public static RequestFormOperType getInvalidValue() {
        return UNDEFINED;
    }

    @JsonCreator
    public static RequestFormOperType valueOfCode(String code) {
        return CommonEnum.valueOfCode(RequestFormOperType.class, code, getInvalidValue());
    }
}
