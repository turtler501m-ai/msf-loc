package com.ktmmobile.msf.commons.websecurity.security.auth.data.memberdetails;

import java.util.Collection;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.ktmmobile.msf.commons.common.data.entity.user.FormUser;

@RequiredArgsConstructor
public class FormUserDetails implements MsfUserDetails {

    private final FormUser user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = new SimpleGrantedAuthority(this.user.getUserType().getCode());
        return List.of(authority);
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public FormUser getUser() {
        return this.user;
    }
}
