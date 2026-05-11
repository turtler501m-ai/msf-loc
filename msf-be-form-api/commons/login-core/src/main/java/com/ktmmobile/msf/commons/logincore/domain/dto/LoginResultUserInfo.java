package com.ktmmobile.msf.commons.logincore.domain.dto;

import java.util.LinkedHashMap;
import java.util.Map;

public record LoginResultUserInfo(
    String userId,
    String userName,
    String phoneNumber,
    String clientIp,
    LoginOrganization organization,
    Map<String, Object> attributes
) {

    public LoginResultUserInfo {
        organization = organization == null ? LoginOrganization.empty() : organization;
        attributes = attributes == null ? new LinkedHashMap<>() : new LinkedHashMap<>(attributes);
    }

    public LoginResultUserInfo(
        String userId,
        String userName,
        String phoneNumber,
        String clientIp,
        String agentCode,
        String agentName,
        String shopCode,
        String shopName,
        Map<String, Object> attributes
    ) {
        this(userId, userName, phoneNumber, clientIp, new LoginOrganization(agentCode, agentName, shopCode, shopName), attributes);
    }

    public static LoginResultUserInfo from(LoginActionRequired required) {
        return new LoginResultUserInfo(
            required.userId(),
            required.userName(),
            required.phoneNumber(),
            required.clientIp(),
            required.organization(),
            required.attributes()
        );
    }

    public static LoginResultUserInfo from(LoginSessionReady ready) {
        return new LoginResultUserInfo(
            ready.userId(),
            ready.userName(),
            ready.phoneNumber(),
            ready.clientIp(),
            ready.organization(),
            ready.attributes()
        );
    }

    public static LoginResultUserInfo from(LoginTwoFactorRequired required) {
        return new LoginResultUserInfo(
            required.userId(),
            required.userName(),
            required.phoneNumber(),
            required.clientIp(),
            required.organization(),
            required.attributes()
        );
    }

    public static LoginResultUserInfo from(LoginTokenPair tokenPair) {
        return new LoginResultUserInfo(
            tokenPair.userId(),
            tokenPair.userName(),
            tokenPair.phoneNumber(),
            tokenPair.clientIp(),
            tokenPair.organization(),
            tokenPair.attributes()
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
