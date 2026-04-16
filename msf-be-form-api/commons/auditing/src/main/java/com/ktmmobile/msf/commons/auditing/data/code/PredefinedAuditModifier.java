package com.ktmmobile.msf.commons.auditing.data.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PredefinedAuditModifier {
    SYSTEM("SYSTEM"),
    SCHEDULER("SCHEDULER"),
    BASIC("-"),
    NULL("");

    private final String code;

    public static String getNullCode() {
        return NULL.getCode();
    }

    public boolean isValid() {
        return this != NULL;
    }
}
