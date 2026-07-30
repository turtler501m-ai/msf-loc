package com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum FaceAuthFormType implements CommonEnum {
    NEWCHANGE("1", "신규/변경", "F-1-FTH"),
    SERVICECHANGE("2", "서비스변경", "F-2-FTH"),
    OWNERCHANGE("3", "명의변경", "F-3-FTH"),
    TERMINATION("4", "서비스해지", "F-4-FTH");

    private final String code;
    private final String title;
    private final String smsType;

    @JsonCreator
    public static FaceAuthFormType from(String code) {
        for (FaceAuthFormType type : FaceAuthFormType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
