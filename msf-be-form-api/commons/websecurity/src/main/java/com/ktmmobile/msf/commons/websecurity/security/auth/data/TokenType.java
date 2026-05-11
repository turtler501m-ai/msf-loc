package com.ktmmobile.msf.commons.websecurity.security.auth.data;

import java.util.Arrays;

public enum TokenType {
    ACCESS("access", "AccessToken"),
    REFRESH("refresh", "RefreshToken");

    private final String claimValue;
    private final String displayName;

    TokenType(String claimValue, String displayName) {
        this.claimValue = claimValue;
        this.displayName = displayName;
    }

    public String getClaimValue() {
        return claimValue;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean matches(String claimValue) {
        return this.claimValue.equals(claimValue);
    }

    public static TokenType valueOfClaim(String claimValue) {
        return Arrays.stream(values())
            .filter(tokenType -> tokenType.matches(claimValue))
            .findFirst()
            .orElse(null);
    }
}
