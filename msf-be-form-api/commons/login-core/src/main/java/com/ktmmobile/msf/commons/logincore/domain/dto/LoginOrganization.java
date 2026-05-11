package com.ktmmobile.msf.commons.logincore.domain.dto;

public record LoginOrganization(
    String agentCode,
    String agentName,
    String shopCode,
    String shopName
) {

    public LoginOrganization {
        agentCode = valueOrEmpty(agentCode);
        agentName = valueOrEmpty(agentName);
        shopCode = valueOrEmpty(shopCode);
        shopName = valueOrEmpty(shopName);
    }

    public static LoginOrganization empty() {
        return new LoginOrganization("", "", "", "");
    }

    private static String valueOrEmpty(String value) {
        return value == null ? "" : value;
    }
}
