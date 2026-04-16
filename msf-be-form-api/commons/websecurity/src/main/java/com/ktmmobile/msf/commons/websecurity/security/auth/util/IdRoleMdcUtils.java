package com.ktmmobile.msf.commons.websecurity.security.auth.util;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.MDC;

import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.header.IdRoleAuthorizationHeader;

public class IdRoleMdcUtils {

    private static final String MDC_MEMBER_ID = "memberId";
    private static final String MDC_MEMBER_ROLE = "memberRole";
    private static final String MDC_ALREADY_SETTED = "alreadySetted";

    public static void putIdRoleToMdc(HttpServletRequest request) {
        if (isAlreadySetted()) {
            return;
        }

        IdRoleAuthorizationHeader authHeader = IdRoleAuthorizationHeader.getFromAttributeOrCreate(request);
        if (authHeader.isValid()) {
            doPutIdRoleToMdc(authHeader.id(), authHeader.role());
        }
    }

    private static void doPutIdRoleToMdc(String id, UserType role) {
        MDC.put(MDC_MEMBER_ID, id);
        MDC.put(MDC_MEMBER_ROLE, role.getSimpleCode());
        changeToAlreadySetted();
    }

    public static void putIdRoleToMdc(String id, UserType role) {
        if (isAlreadySetted()) {
            return;
        }
        doPutIdRoleToMdc(id, role);
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
