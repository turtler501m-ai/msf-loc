package com.ktmmobile.msf.domains.eformsign.feature.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnumConstant;

@Getter
@RequiredArgsConstructor
public enum CstmrType implements CommonEnum {
    NATIVEADULT("NA", "내국인"),
    NATIVEMINOR("NM", "내국인 미성년자(19세 미만)"),
    FOREIGNERADULT("FN", "외국인"),
    FOREIGNERMINOR("FM", "외국인 미성년자(19세 미만)"),
    JURIDICALPERSON("JP", "법인"),
    GOVERNMENTORGANIZATION("GO", "공공기관"),

    @Deprecated
    UNDEFINED(CommonEnumConstant.UNDEFINED_CODE, "Invalid CstmrType");

    private final String code;
    private final String title;

    public String getAuthField() {
        return switch (this) {
            case NATIVEADULT, NATIVEMINOR -> "cstmr_native_birth";
            case FOREIGNERADULT, FOREIGNERMINOR -> "cstmr_foreigner_birth";
            case JURIDICALPERSON -> "cstmr_juridical_rrn";
            case GOVERNMENTORGANIZATION -> "cstmr_private_biz_no";
            default -> throw new IllegalArgumentException("Invalid CstmrTypeCd");
        };
    }

    @Override
    public boolean isValid() {
        return this != getInvalidValue();
    }

    public static CstmrType getInvalidValue() {
        return UNDEFINED;
    }

    @JsonCreator
    public static CstmrType valueOfCode(String code) {
        return CommonEnum.valueOfCode(CstmrType.class, code, getInvalidValue());
    }

}
