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
    String clientIp,
    LoginOrganization organization,
    Map<String, Object> attributes
) {

    public LoginUserInfo {
        organization = organization == null ? LoginOrganization.empty() : organization;
        attributes = attributes == null ? new LinkedHashMap<>() : new LinkedHashMap<>(attributes);
    }

    public LoginUserInfo(
        String userId,
        String userName,
        String phoneNumber,
        UserType userType,
        String clientIp,
        Map<String, Object> attributes
    ) {
        this(userId, userName, phoneNumber, userType, clientIp, LoginOrganization.empty(), attributes);
    }

    public LoginUserInfo(
        String userId,
        String userName,
        String phoneNumber,
        UserType userType,
        String clientIp,
        String agentCode,
        String agentName,
        String shopCode,
        String shopName,
        Map<String, Object> attributes
    ) {
        this(userId, userName, phoneNumber, userType, clientIp, new LoginOrganization(agentCode, agentName, shopCode, shopName), attributes);
    }

    public static LoginUserInfo of(LoginUser user, UserType userType) {
        return new LoginUserInfo(
            user.userId(),
            user.userName(),
            user.phoneNumber(),
            userType,
            null,
            LoginOrganization.empty(),
            Map.of()
        );
    }

    public String agentCode() {
        return organization.agentCode();
    }

    public String agentName() {
        return organization.agentName();
    }

    public String shopCode() {
        return organization.shopCode();
    }

    public String shopName() {
        return organization.shopName();
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
