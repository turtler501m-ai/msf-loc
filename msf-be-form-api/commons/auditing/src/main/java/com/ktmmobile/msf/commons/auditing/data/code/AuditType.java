package com.ktmmobile.msf.commons.auditing.data.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor
public enum AuditType {
    FORM_USER("USER", "F", "일반 사용자"),
    ADMIN_USER("ADMIN", "A", "관리자"),
    UNKNOWN("", "", "미확인");

    private final String code;
    private final String simpleCode;
    private final String title;

    public static AuditType valueOfCode(String code) {
        if (!StringUtils.hasText(code)) {
            return getDefaultValue();
        }
        for (AuditType value: values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return getDefaultValue();
    }

    public static AuditType valueOfSimpleCode(String simpleCode) {
        if (!StringUtils.hasText(simpleCode)) {
            return getDefaultValue();
        }
        for (AuditType value: values()) {
            if (value.getSimpleCode().equals(simpleCode)) {
                return value;
            }
        }
        return getDefaultValue();
    }

    public static AuditType getDefaultValue() {
        return UNKNOWN;
    }
}
