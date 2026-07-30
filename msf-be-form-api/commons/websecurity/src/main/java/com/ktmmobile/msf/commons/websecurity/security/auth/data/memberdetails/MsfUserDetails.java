package com.ktmmobile.msf.commons.websecurity.security.auth.data.memberdetails;

import org.springframework.security.core.userdetails.UserDetails;

import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;
import com.ktmmobile.msf.commons.common.data.type.UserType;

public interface MsfUserDetails extends UserDetails {

    String ROLE_PREFIX = "ROLE_";

    boolean isAdmin();

    MsfUser getUser();

    static String roleAuthority(UserType userType) {
        return ROLE_PREFIX + userType.getCode();
    }

    @Override
    default String getPassword() {
        return null;
    }

    @Override
    default String getUsername() {
        return null;
    }

}
