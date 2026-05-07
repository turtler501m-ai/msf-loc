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
    Map<String, Object> attributes,
    List<LoginRequiredAction> requiredActions
) implements LoginResult {

    public LoginActionRequired {
        attributes = attributes == null ? new LinkedHashMap<>() : new LinkedHashMap<>(attributes);
        requiredActions = requiredActions == null ? new ArrayList<>() : new ArrayList<>(requiredActions);
    }
}
