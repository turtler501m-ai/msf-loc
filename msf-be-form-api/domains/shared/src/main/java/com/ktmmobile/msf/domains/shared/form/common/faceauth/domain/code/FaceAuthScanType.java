package com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum FaceAuthScanType implements CommonEnum {
    ID("I", "신분증 스캐너"),
    MOBILE("M", "모바일 신분증"),
    FINANCE("F", "금융 스캐너");

    private final String code;
    private final String title;

    @JsonCreator
    public static FaceAuthScanType from(String code) {
        for (FaceAuthScanType type: FaceAuthScanType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
