package com.ktmmobile.msf.commons.websecurity.security.auth.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ktmmobile.msf.commons.common.data.entity.user.AdminUser;
import com.ktmmobile.msf.commons.common.data.entity.user.FormUser;
import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.memberdetails.AdminUserDetails;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.memberdetails.FormUserDetails;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.memberdetails.MsfUserDetails;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationUtils {

    public static MsfUser getUser() {
        return getUserDetails().getUser();
    }

    public static FormUser getFormUser() {
        return getFormUserDetails().getUser();
    }

    public static AdminUser getAdminUser() {
        return getAdminUserDetails().getUser();
    }

    public static FormUserDetails getFormUserDetails() {
        return (FormUserDetails) getUserDetails();
    }

    public static AdminUserDetails getAdminUserDetails() {
        return (AdminUserDetails) getUserDetails();
    }

    private static MsfUserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        if (authentication != null) {
            return (MsfUserDetails) authentication.getPrincipal();
        }
        throw new AuthenticationCredentialsNotFoundException("인증 객체를 조회할 수 없습니다.");
    }
}
