package com.ktmmobile.msf.commons.logincore.domain.code;

public enum TokenType {
    ACCESS("access"),
    REFRESH("refresh");

    private final String claimValue;

    TokenType(String claimValue) {
        this.claimValue = claimValue;
    }

    public String getClaimValue() {
        return claimValue;
    }
}
