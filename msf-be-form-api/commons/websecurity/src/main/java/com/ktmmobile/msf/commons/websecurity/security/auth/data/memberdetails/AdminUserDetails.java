package com.ktmmobile.msf.commons.websecurity.security.auth.data.memberdetails;

import java.util.Collection;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.ktmmobile.msf.commons.common.data.entity.user.AdminUser;

@RequiredArgsConstructor
public class AdminUserDetails implements MsfUserDetails {

    private final AdminUser user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = new SimpleGrantedAuthority(this.user.getUserType().getCode());
        return List.of(authority);
    }

    @Override
    public boolean isAdmin() {
        return true;
    }

    @Override
    public AdminUser getUser() {
        return this.user;
    }
}
