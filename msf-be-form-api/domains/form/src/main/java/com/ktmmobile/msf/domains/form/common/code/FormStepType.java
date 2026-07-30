package com.ktmmobile.msf.domains.form.common.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnumConstant;

@Getter
@RequiredArgsConstructor
public enum FormStepType implements CommonEnum {
    CUSTOMER_STEP("1", "고객단계"),
    PRODUCT_STEP("2", "상품단계"),
    AGREE_STEP("3", "동의단계"),
    COMPLETE_STEP("4", "작성완료"),

    @Deprecated
    UNDEFINED(CommonEnumConstant.UNDEFINED_CODE, "Invalid FormStepType");

    private final String code;
    private final String title;

    @Override
    public boolean isValid() {
        return this != getInvalidValue();
    }

    public static FormStepType getInvalidValue() {
        return UNDEFINED;
    }

    @JsonCreator
    public static FormStepType valueOfCode(String code) {
        return CommonEnum.valueOfCode(FormStepType.class, code, getInvalidValue());
    }
}
