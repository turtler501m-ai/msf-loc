package com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FaceAuthCustomerType {
    NATIONAL("NA", "내국인"),
    MINOR("NM", "내국인 미성년자(19세 미만)"),
    FOREIGNER("FN", "외국인"),
    FOREIGN_MINOR("FM", "외국인 미성년자(19세 미만)"),
    CORPORATION("JP", "법인"),
    GOVERNMENT("GO", "공공기관");

    private final String code;
    private final String title;

    @JsonCreator
    public static FaceAuthCustomerType from(String code) {
        for (FaceAuthCustomerType type : FaceAuthCustomerType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
