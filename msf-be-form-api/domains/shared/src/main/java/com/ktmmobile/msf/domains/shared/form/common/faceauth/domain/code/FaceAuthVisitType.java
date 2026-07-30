package com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum FaceAuthVisitType implements CommonEnum {
    VMY("VMY", "본인/대표"),
    VCD("VCD", "법정대리인"),
    VDP("VDP", "대리인");

    private final String code;
    private final String title;

    @JsonCreator
    public static FaceAuthVisitType forValue(String code) {
        for (FaceAuthVisitType type : FaceAuthVisitType.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }
}
