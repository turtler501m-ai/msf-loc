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
    String clientIp,
    LoginOrganization organization,
    Map<String, Object> attributes,
    List<LoginRequiredAction> requiredActions
) {

    public LoginSessionUser(String userId, String userName, UserType userType, String phoneNumber) {
        this(userId, userType, userName, phoneNumber, null, LoginOrganization.empty(), Map.of(), List.of());
    }

    public LoginSessionUser(
        String userId,
        String userName,
        UserType userType,
        String phoneNumber,
        List<LoginRequiredAction> requiredActions
    ) {
        this(userId, userType, userName, phoneNumber, null, LoginOrganization.empty(), Map.of(), requiredActions);
    }

    public LoginSessionUser(
        String userId,
        UserType userType,
        String userName,
        String phoneNumber,
        String clientIp,
        Map<String, Object> attributes,
        List<LoginRequiredAction> requiredActions
    ) {
        this(userId, userType, userName, phoneNumber, clientIp, LoginOrganization.empty(), attributes, requiredActions);
    }

    public LoginSessionUser(
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
        this(userId, userType, userName, phoneNumber, clientIp, new LoginOrganization(agentCode, agentName, shopCode, shopName), attributes, requiredActions);
    }

    public LoginSessionUser {
        organization = organization == null ? LoginOrganization.empty() : organization;
        attributes = attributes == null ? new LinkedHashMap<>() : new LinkedHashMap<>(attributes);
        requiredActions = requiredActions == null ? new ArrayList<>() : new ArrayList<>(requiredActions);
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

    public String levelCode() {
        return organization.levelCode();
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
