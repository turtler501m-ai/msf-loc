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
    Map<String, Object> attributes
) implements LoginResult {

    public LoginSessionReady {
        attributes = attributes == null ? new LinkedHashMap<>() : new LinkedHashMap<>(attributes);
    }
}
