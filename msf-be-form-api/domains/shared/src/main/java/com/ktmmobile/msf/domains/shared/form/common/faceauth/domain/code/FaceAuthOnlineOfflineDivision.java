package com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum FaceAuthOnlineOfflineDivision implements CommonEnum {
    ONLINE("ONLINE", "온라인", "W"),
    OFFLINE("OFFLINE", "오프라인", "D");

    private final String code;
    private final String title;
    private final String photoCode;

    @JsonCreator
    public static FaceAuthOnlineOfflineDivision from(String code) {
        for (FaceAuthOnlineOfflineDivision division : FaceAuthOnlineOfflineDivision.values()) {
            if (division.getCode().equals(code)) {
                return division;
            }
        }
        return null;
    }
}
