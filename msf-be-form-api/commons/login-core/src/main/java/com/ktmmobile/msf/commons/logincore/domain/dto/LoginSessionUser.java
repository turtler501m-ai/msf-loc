package com.ktmmobile.msf.commons.logincore.domain.dto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ktmmobile.msf.commons.common.data.type.UserType;

public record LoginSessionUser(
    String userId,
    UserType userType,
    String userName,
    String phoneNumber,
    Map<String, Object> attributes,
    List<LoginRequiredAction> requiredActions
) {

    public LoginSessionUser(String userId, String userName, UserType userType, String phoneNumber) {
        this(userId, userType, userName, phoneNumber, Map.of(), List.of());
    }

    public LoginSessionUser(
        String userId,
        String userName,
        UserType userType,
        String phoneNumber,
        List<LoginRequiredAction> requiredActions
    ) {
        this(userId, userType, userName, phoneNumber, Map.of(), requiredActions);
    }

    public LoginSessionUser {
        attributes = attributes == null ? new LinkedHashMap<>() : new LinkedHashMap<>(attributes);
        requiredActions = requiredActions == null ? new ArrayList<>() : new ArrayList<>(requiredActions);
    }

    public String attributeAsString(String name) {
        Object value = attributes.get(name);
        return value == null ? null : String.valueOf(value);
    }

    public Boolean attributeAsBoolean(String name) {
        Object value = attributes.get(name);
        if (value instanceof Boolean booleanValue) {
            return booleanValue;
        }
        return value == null ? null : Boolean.valueOf(String.valueOf(value));
    }
}
