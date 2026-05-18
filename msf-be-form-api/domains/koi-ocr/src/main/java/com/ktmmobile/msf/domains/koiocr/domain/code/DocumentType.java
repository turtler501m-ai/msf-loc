package com.ktmmobile.msf.domains.koiocr.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum DocumentType implements CommonEnum {
    RESIDENT_REGISTRATION_CARD("01", "주민등록증"),
    DRIVER_LICENSE("02", "운전면허증"),
    DISABILITY_CARD("03", "장애인등록증"),
    NATIONAL_MERIT_CARD("04", "국가유공자증"),
    PASSPORT_FOR_FOREIGNER("05", "여권(외국인)"),
    FOREIGNER_REGISTRATION_CARD("06", "외국인등록증");

    private final String code;
    private final String title;

    @JsonCreator
    public static DocumentType valueOfCode(String code) {
        return CommonEnum.valueOfCode(DocumentType.class, code);
    }
}