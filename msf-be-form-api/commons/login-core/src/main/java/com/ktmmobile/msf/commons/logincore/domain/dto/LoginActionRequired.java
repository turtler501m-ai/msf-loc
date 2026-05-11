package com.ktmmobile.msf.commons.logincore.domain.dto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ktmmobile.msf.commons.common.data.type.UserType;

public record LoginActionRequired(
    String loginSessionId,
    String userId,
    UserType userType,
    String userName,
    String phoneNumber,
    String clientIp,
    LoginOrganization organization,
    Map<String, Object> attributes,
    List<LoginRequiredAction> requiredActions
) implements LoginResult {

    public LoginActionRequired(
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
        Map<String, Object> attributes,
        List<LoginRequiredAction> requiredActions
    ) {
        this(loginSessionId, userId, userType, userName, phoneNumber, clientIp,
            new LoginOrganization(agentCode, agentName, shopCode, shopName), attributes, requiredActions);
    }

    public LoginActionRequired {
        organization = organization == null ? LoginOrganization.empty() : organization;
        attributes = attributes == null ? new LinkedHashMap<>() : new LinkedHashMap<>(attributes);
        requiredActions = requiredActions == null ? new ArrayList<>() : new ArrayList<>(requiredActions);
    }
}
