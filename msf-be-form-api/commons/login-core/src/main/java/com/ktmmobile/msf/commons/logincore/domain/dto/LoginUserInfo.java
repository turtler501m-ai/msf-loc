package com.ktmmobile.msf.commons.logincore.domain.dto;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.logincore.domain.entity.LoginUser;

public record LoginUserInfo(
    String userId,
    String userName,
    String phoneNumber,
    UserType userType,
    Map<String, Object> attributes
) {

    public LoginUserInfo {
        attributes = attributes == null ? new LinkedHashMap<>() : new LinkedHashMap<>(attributes);
    }

    public static LoginUserInfo of(LoginUser user, UserType userType) {
        return new LoginUserInfo(
            user.userId(),
            user.userName(),
            user.phoneNumber(),
            userType,
            Map.of()
        );
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
