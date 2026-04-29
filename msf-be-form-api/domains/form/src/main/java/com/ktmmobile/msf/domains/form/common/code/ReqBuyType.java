package com.ktmmobile.msf.domains.form.common.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnumConstant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReqBuyType implements CommonEnum {
    MOBILE("MM", "01", "휴대폰"),
    USIM("UU", "02","USIM"),

    @Deprecated
    UNDEFINED(CommonEnumConstant.UNDEFINED_CODE, "00","Invalid ReqBuyType");

    private final String code;
    private final String rateType; //reqBuyType.rateType 호출시 getRateType()기능
    private final String title;

    public boolean isMobile() {
        return this == MOBILE;
    }

    public boolean isUsim() {
        return this == USIM;
    }

    @Override
    public boolean isValid() {
        return this != getInvalidValue();
    }

    public static ReqBuyType getInvalidValue() {
        return UNDEFINED;
    }

    @JsonCreator
    public static ReqBuyType valueOfCode(String code) {
        return CommonEnum.valueOfCode(ReqBuyType.class, code, getInvalidValue());
    }
}
