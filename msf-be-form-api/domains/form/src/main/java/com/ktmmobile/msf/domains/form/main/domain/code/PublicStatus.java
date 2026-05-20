package com.ktmmobile.msf.domains.form.main.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum PublicStatus implements CommonEnum {
    Y("Y", "공개"),
    N("N", "비공개");

    private final String code;
    private final String title;

    @JsonCreator
    public static PublicStatus from(String code) {
        for (PublicStatus status : PublicStatus.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
