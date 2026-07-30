package com.ktmmobile.msf.domains.form.common.util;

import com.ktmmobile.msf.domains.form.common.dto.SiteMenuDto;
import com.ktmmobile.msf.domains.form.common.dto.UserSessionDto;
import com.ktmmobile.msf.domains.form.common.dto.WorkNotiDto;

/**
 * FIXME: 제거 대상
 */
public class SessionUtils {

    public static final String USER_SESSION = "USER_SESSION";

    private SessionUtils() { }

    public static UserSessionDto getUserCookieBean() {
        return null;
    }

    public static SiteMenuDto getCurrentMenuDto() {
        return null;
    }

    public static WorkNotiDto getCurrentMenuUrl() {
        return null;
    }

    public static long getMaskingSession() {
        return 0;
    }

    public static void saveOneTimePopup(String oneTimePopupGrp) {
        // No Op
    }

    public static String getOneTimePopup() {
        return null;
    }
}
