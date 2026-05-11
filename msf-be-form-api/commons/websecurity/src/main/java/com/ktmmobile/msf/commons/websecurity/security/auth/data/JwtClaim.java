package com.ktmmobile.msf.commons.websecurity.security.auth.data;

public enum JwtClaim {
    TOKEN_TYPE("tokenType"),
    USER_TYPE("userType");

    private final String key;

    JwtClaim(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
