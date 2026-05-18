package com.ktmmobile.msf.commons.common.data.entity.user;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.data.type.UserType;

@Getter
@RequiredArgsConstructor
public class AdminUser implements MsfUser, Serializable {

    private final UserType userType;
    private final String userId;
    private final String userName;
    private final UserOrganization organization;

    public String getAgentCode() {
        return organization.agentCode();
    }

    public String getAgentName() {
        return organization.agentName();
    }

    public String getShopCode() {
        return organization.shopCode();
    }

    public String getShopName() {
        return organization.shopName();
    }

    public String getOrganizationLevelCode() {
        return organization.levelCode();
    }
}
