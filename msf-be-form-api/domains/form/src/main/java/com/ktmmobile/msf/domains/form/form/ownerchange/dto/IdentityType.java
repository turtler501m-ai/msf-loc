package com.ktmmobile.msf.domains.form.form.ownerchange.dto;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IdentityType {
    RESIDENT_ID("01", "주민등록증", "01", "REGID"),
    DRIVE_LICENSE("02", "운전면허증", "01", "DRIVE"),
    DISABLED_ID("03", "장애인등록증", "01", "HANDI"),
    MERIT_ID("04", "국가유공자증", "01", "MERIT"),
    PASSPORT_FORGN("05", "여권(외국인)", "04", "PPORT"),
    FOREIGNER_ID("06", "외국인등록증", "05", "FORGN");

    private final String code;
    private final String name;
    private final String groupCode;
    private final String targetCode;

    public static IdentityType getIdentityTypeByCode(String code) {
        return Arrays.stream(values())
            .filter(type -> type.code.equals(code))
            .findFirst()
            .orElse(null);
    }
}
