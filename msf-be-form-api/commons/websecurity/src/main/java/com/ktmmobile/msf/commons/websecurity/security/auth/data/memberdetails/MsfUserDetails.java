package com.ktmmobile.msf.commons.websecurity.security.auth.data.memberdetails;

import org.springframework.security.core.userdetails.UserDetails;

import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;

public interface MsfUserDetails extends UserDetails {

    boolean isAdmin();

    MsfUser getUser();

    @Override
    default String getPassword() {
        return null;
    }

    @Override
    default String getUsername() {
        return null;
    }

}
