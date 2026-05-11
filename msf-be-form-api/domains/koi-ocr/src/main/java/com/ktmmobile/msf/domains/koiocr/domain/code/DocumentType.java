package com.ktmmobile.msf.domains.shared.form.common.ocr.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DocumentType {
    RESIDENT_REGISTRATION_CARD("01", "주민등록증"),
    DRIVER_LICENSE("02", "운전면허증"),
    NATIONAL_MERIT_CARD("04", "국가유공자증"),
    PASSPORT_FOR_FOREIGNER("05", "여권(외국인)"),
    FOREIGNER_REGISTRATION_CARD("06", "외국인등록증");

    private final String code;
    private final String value;
}