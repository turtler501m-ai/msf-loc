package com.ktmmobile.msf.commons.common.data.entity.user;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.data.type.UserType;

@Getter
@RequiredArgsConstructor
public class FormUser implements MsfUser, Serializable {

    private final String id;
    private final String name;
    private final UserType userType;
    private final String agentCode;
    private final String shopCode;

    public FormUser(String id, String name, UserType userType) {
        this(id, name, userType, null, null);
    }
}
