package com.ktmmobile.msf.domains.form.common.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnumConstant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomerType implements CommonEnum {
    NATIVE_ADULT("NA","I", "내국인 성인"),
    NATIVE_MINOR("NM", "I", "내국인 미성년자"),
    FOREIGN_ADULT("FN", "I", "외국인 성인"),
    FOREIGN_MINOR("FM", "I", "외국인 미성년자"),
    JURIDICAL_PERSON("JP", "B", "법인"),
    GOVERNMENT_ORGANIZATION("GO", "G", "공공기관"),

    @Deprecated
    UNDEFINED(CommonEnumConstant.UNDEFINED_CODE, "I", "Invalid CustomerType");

    private final String code;
    private final String subCode;
    private final String title;

    @Override
    public boolean isValid() {
        return this != getInvalidValue();
    }

    public static CustomerType getInvalidValue() {
        return UNDEFINED;
    }

    @JsonCreator
    public static CustomerType valueOfCode(String code) {
        return CommonEnum.valueOfCode(CustomerType.class, code, getInvalidValue());
    }
}
