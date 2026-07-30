package com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum FaceAuthPhotoAuthServiceDivision implements CommonEnum {
    MOBILE("MOB", "모바일"),
    ETC("ETC", "기타");

    private final String code;
    private final String title;

    @JsonCreator
    public static FaceAuthPhotoAuthServiceDivision parse(String code) {
        for (FaceAuthPhotoAuthServiceDivision type : FaceAuthPhotoAuthServiceDivision.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
