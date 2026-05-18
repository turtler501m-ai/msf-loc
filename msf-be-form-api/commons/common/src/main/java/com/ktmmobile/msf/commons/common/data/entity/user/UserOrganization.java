package com.ktmmobile.msf.commons.common.data.entity.user;

import java.io.Serializable;

public record UserOrganization(
    String agentCode,
    String agentName,
    String shopCode,
    String shopName,
    String levelCode
) implements Serializable {

    public UserOrganization(String agentCode, String agentName, String shopCode, String shopName) {
        this(agentCode, agentName, shopCode, shopName, "");
    }

    public UserOrganization {
        agentCode = valueOrEmpty(agentCode);
        agentName = valueOrEmpty(agentName);
        shopCode = valueOrEmpty(shopCode);
        shopName = valueOrEmpty(shopName);
        levelCode = valueOrEmpty(levelCode);
    }

    public static UserOrganization empty() {
        return new UserOrganization("", "", "", "", "");
    }

    private static String valueOrEmpty(String value) {
        return value == null ? "" : value;
    }
}
