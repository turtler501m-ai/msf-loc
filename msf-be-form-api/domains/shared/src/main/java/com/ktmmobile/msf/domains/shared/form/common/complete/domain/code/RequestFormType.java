package com.ktmmobile.msf.domains.shared.form.common.complete.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnumConstant;

@Getter
@RequiredArgsConstructor
public enum RequestFormType implements CommonEnum {
    NEWCHANGE("1", "신규/변경", "/form/newchange"),
    SERVICECHANGE("2", "서비스변경", "/form/servicechange"),
    OWNERCHANGE("3", "명의변경", "/form/ownerchange"),
    TERMINATION("4", "서비스해지", "/form/termination"),

    @Deprecated
    UNDEFINED(CommonEnumConstant.UNDEFINED_CODE, "Invalid FormType", "");

    private final String code;
    private final String title;
    private final String path;

    public String getRequestResource() {
        return switch (this) {
            case NEWCHANGE -> "msf_request";
            case SERVICECHANGE -> "msf_request_svc_chg";
            case OWNERCHANGE -> "msf_request_name_chg";
            case TERMINATION -> "msf_request_cancel";
            default -> throw new IllegalArgumentException("Invalid FormTypeCd");
        };
    }

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

    public static RequestFormType getInvalidValue() {
        return UNDEFINED;
    }

    @JsonCreator
    public static RequestFormType valueOfCode(String code) {
        return CommonEnum.valueOfCode(RequestFormType.class, code, getInvalidValue());
    }
}
