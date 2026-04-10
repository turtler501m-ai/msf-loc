package com.ktmmobile.msf.commons.common.data.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum SystemType implements CommonEnum {
    BO("BO", "BO 시스템"),
    FO("FO", "FO 시스템");

    private final String code;
    private final String title;

    public boolean isBo() {
        return this == BO;
    }

    public boolean isFo() {
        return this == FO;
    }
}
