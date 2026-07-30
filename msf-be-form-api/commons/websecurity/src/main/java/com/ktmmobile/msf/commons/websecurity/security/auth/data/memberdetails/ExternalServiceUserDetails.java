package com.ktmmobile.msf.commons.websecurity.security.auth.data.memberdetails;

import java.util.Collection;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import com.ktmmobile.msf.commons.common.data.entity.user.ExternalServiceUser;

@RequiredArgsConstructor
public class ExternalServiceUserDetails implements MsfUserDetails {

    private final ExternalServiceUser user;
    private final List<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public ExternalServiceUser getUser() {
        return this.user;
    }

    @Override
    public String getUsername() {
        return this.user.getUserId();
    }
}
