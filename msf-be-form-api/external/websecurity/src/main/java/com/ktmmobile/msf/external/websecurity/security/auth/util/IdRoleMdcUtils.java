package com.ktmmobile.msf.external.websecurity.security.auth.util;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.MDC;

import com.ktmmobile.msf.external.websecurity.security.auth.data.JwtAuthenticatedMember;

public class IdRoleMdcUtils {

    private static final String MDC_MEMBER_ID = "memberId";
    private static final String MDC_ALREADY_SETTED = "alreadySetted";

    public static void putIdRoleToMdc(HttpServletRequest request) {
        if (isAlreadySetted()) {
            return;
        }

        JwtAuthenticatedMember member = AuthenticationUtils.getAuthenticatedMemberOrNull();
        if (member != null) {
            doPutIdRoleToMdc(member.getUserId());
        }
    }

    private static void doPutIdRoleToMdc(String id) {
        MDC.put(MDC_MEMBER_ID, id);
        changeToAlreadySetted();
    }

    public static void putIdRoleToMdc(String id) {
        if (isAlreadySetted()) {
            return;
        }
        doPutIdRoleToMdc(id);
    }

    private static boolean isAlreadySetted() {
        String setted = MDC.get(MDC_ALREADY_SETTED);
        return SettedType.SETTED.name().equals(setted);
    }

    private static void changeToAlreadySetted() {
        MDC.put(MDC_ALREADY_SETTED, SettedType.SETTED.name());
    }


    enum SettedType {
        SETTED, UNSETTED
    }
}
