package com.ktmmobile.msf.commons.common.utils.env;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnvironmentProfile {
    LOCAL("local"),
    DEVELOPMENT("dev"),
    STAGING("stg"),
    PRODUCTION("prd");

    private final String code;

    public static EnvironmentProfile valueOfCode(String code) {
        for (EnvironmentProfile value: values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static boolean isLocal(String profile) {
        return getLocal().getCode().equals(profile);
    }

    public boolean isLocal() {
        return this == getLocal();
    }

    public static EnvironmentProfile getLocal() {
        return LOCAL;
    }

    public static boolean isDevelopment(String profile) {
        return getDevelopment().getCode().equals(profile);
    }

    public boolean isDevelopment() {
        return this == getDevelopment();
    }

    public static EnvironmentProfile getDevelopment() {
        return DEVELOPMENT;
    }

    public static boolean isProduction(String profile) {
        return getProduction().getCode().equals(profile);
    }

    public boolean isProduction() {
        return this == getProduction();
    }

    public static EnvironmentProfile getProduction() {
        return PRODUCTION;
    }
}
