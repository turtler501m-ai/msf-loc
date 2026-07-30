package com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum FaceAuthIdentityType implements CommonEnum {
    REGID("01", "주민등록증", "REGID", "I"),
    DRIVE("02", "운전면허증", "DRIVE", "D"),
    HANDI("03", "장애인등록증", "HANDI", ""),
    MERIT("04", "국가유공자증", "MERIT", ""),
    PPORT("05", "여권(외국인)", "PPORT", ""),
    FORGN("06", "외국인등록증", "FORGN", "");

    private final String code;
    private final String title;
    private final String apiCode;
    private final String apiResponseCode;

    @JsonCreator
    public static FaceAuthIdentityType from(String code) {
        for (FaceAuthIdentityType type: FaceAuthIdentityType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
