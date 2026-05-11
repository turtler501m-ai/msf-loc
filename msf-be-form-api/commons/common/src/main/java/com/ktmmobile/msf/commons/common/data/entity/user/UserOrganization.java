package com.ktmmobile.msf.commons.common.data.entity.user;

import java.io.Serializable;

public record UserOrganization(
    String agentCode,
    String agentName,
    String shopCode,
    String shopName
) implements Serializable {

    public UserOrganization {
        agentCode = valueOrEmpty(agentCode);
        agentName = valueOrEmpty(agentName);
        shopCode = valueOrEmpty(shopCode);
        shopName = valueOrEmpty(shopName);
    }

    public static UserOrganization empty() {
        return new UserOrganization("", "", "", "");
    }

    private static String valueOrEmpty(String value) {
        return value == null ? "" : value;
    }
}
