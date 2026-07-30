package com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum FaceAuthIdentityForm implements CommonEnum {
    KNOTE_IDCARD("I", "K-NOTE 신분증 스캔"),
    MOBILE_IDCARD("M", "모바일 신분증");

    private final String code;
    private final String title;

    @JsonCreator
    public static FaceAuthIdentityForm from(String code) {
        for (FaceAuthIdentityForm type: FaceAuthIdentityForm.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
