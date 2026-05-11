package com.ktmmobile.msf.commons.logincore.domain.dto;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ktmmobile.msf.commons.common.data.type.UserType;

public record LoginSessionReady(
    String loginSessionId,
    String userId,
    UserType userType,
    String userName,
    String phoneNumber,
    String clientIp,
    LoginOrganization organization,
    Map<String, Object> attributes
) implements LoginResult {

    public LoginSessionReady(
        String loginSessionId,
        String userId,
        UserType userType,
        String userName,
        String phoneNumber,
        String clientIp,
        String agentCode,
        String agentName,
        String shopCode,
        String shopName,
        Map<String, Object> attributes
    ) {
        this(loginSessionId, userId, userType, userName, phoneNumber, clientIp,
            new LoginOrganization(agentCode, agentName, shopCode, shopName), attributes);
    }

    public LoginSessionReady {
        organization = organization == null ? LoginOrganization.empty() : organization;
        attributes = attributes == null ? new LinkedHashMap<>() : new LinkedHashMap<>(attributes);
    }
}
