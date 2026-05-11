package com.ktmmobile.msf.domains.policy.organization;

import java.util.Objects;

public enum OrganizationType {
    HEAD_OFFICE("10", null),
    AGENCY("20", "10"),
    SALES_AGENCY("30", "20");

    private final String typeCode;
    private final String levelCode;

    OrganizationType(String typeCode, String levelCode) {
        this.typeCode = typeCode;
        this.levelCode = levelCode;
    }

    public static boolean isAgency(String typeCode, String levelCode) {
        return AGENCY.matches(typeCode, levelCode);
    }

    public static boolean isSalesAgency(String typeCode, String levelCode) {
        return SALES_AGENCY.matches(typeCode, levelCode);
    }

    public static boolean isHeadOffice(String typeCode) {
        return HEAD_OFFICE.matches(typeCode, null);
    }

    private boolean matches(String typeCode, String levelCode) {
        return Objects.equals(this.typeCode, typeCode)
            && (this.levelCode == null || Objects.equals(this.levelCode, levelCode));
    }
}
