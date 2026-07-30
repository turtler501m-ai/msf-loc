package com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum FaceAuthPhotoAuthRequestDivision implements CommonEnum {
    BEFORE("01", "개통전", "BE"),
    AFTER("02", "개통후 재처리", "AF"),
    CDM("11", "CDM재처리", "AF"),
    ORDER("12", "오더재처리", "AF");

    private final String code;
    private final String title;
    private final String photoCode;

    @JsonCreator
    public static FaceAuthPhotoAuthRequestDivision from(String code){
        for (FaceAuthPhotoAuthRequestDivision division : FaceAuthPhotoAuthRequestDivision.values()) {
            if (division.getCode().equals(code)) {
                return division;
            }
        }
        return null;
    }
}
