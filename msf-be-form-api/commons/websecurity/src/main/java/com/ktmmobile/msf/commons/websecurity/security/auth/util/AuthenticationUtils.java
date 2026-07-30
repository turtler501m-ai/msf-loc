package com.ktmmobile.msf.commons.websecurity.security.auth.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ktmmobile.msf.commons.common.data.entity.user.AdminUser;
import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.memberdetails.MsfUserDetails;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationUtils {

    public static MsfUser getUser() {
        return getUserDetails().getUser();
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

    public static String getOrganizationLevelCode() {
        return getUser().getOrganization().levelCode();
    }

    public static String getRoleCode() {
        MsfUser user = getUser();
        if (user instanceof AdminUser adminUser) {
            return adminUser.getRoleCode();
        }
        return null;
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
