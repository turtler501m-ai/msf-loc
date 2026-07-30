package com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum FaceAuthJoinType implements CommonEnum {
    NEW("NAC3", "신규가입", "1"),
    MOD("MNP3", "번호이동", "2"),
    CHANGE("HDN3", "기기변경", "4"),
    OWNERCHANGE("MCN3", "명의변경", "3");

    private final String code;
    private final String title;
    private final String fathDivCode;

    @JsonCreator
    public static FaceAuthJoinType from(String code) {
        for (FaceAuthJoinType type: FaceAuthJoinType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
