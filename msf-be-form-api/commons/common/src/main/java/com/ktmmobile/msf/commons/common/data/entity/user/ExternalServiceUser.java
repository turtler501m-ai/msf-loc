package com.ktmmobile.msf.commons.common.data.entity.user;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.data.type.UserType;

/**
 * 외부 서비스 인증 사용자 엔터티
 */
@Getter
@RequiredArgsConstructor
public class ExternalServiceUser implements MsfUser, Serializable {

    private final UserType userType;
    private final String userId;
    private final String userName;
    private final UserOrganization organization;
}
