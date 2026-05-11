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

    public static String getAgentCode() {
        return getUser().getOrganization().agentCode();
    }

    public static String getAgentName() {
        return getUser().getOrganization().agentName();
    }

    public static String getShopCode() {
        return getUser().getOrganization().shopCode();
    }

    public static String getShopName() {
        return getUser().getOrganization().shopName();
    }

    public static AdminUser getAdminUser() {
        return getAdminUserDetails().getUser();
    }

    public static FormUserDetails getFormUserDetails() {
        MsfUserDetails userDetails = getUserDetails();
        if (userDetails instanceof FormUserDetails formUserDetails) {
            return formUserDetails;
        }
        throw new AuthenticationCredentialsNotFoundException("FORM 사용자 인증 객체가 아닙니다.");
    }

    public static AdminUserDetails getAdminUserDetails() {
        MsfUserDetails userDetails = getUserDetails();
        if (userDetails instanceof AdminUserDetails adminUserDetails) {
            return adminUserDetails;
        }
        throw new AuthenticationCredentialsNotFoundException("ADMIN 사용자 인증 객체가 아닙니다.");
    }

    private static MsfUserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthenticationCredentialsNotFoundException("인증 객체를 조회할 수 없습니다.");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof MsfUserDetails userDetails) {
            return userDetails;
        }
        throw new AuthenticationCredentialsNotFoundException("인증 사용자 정보를 조회할 수 없습니다.");
    }
}
