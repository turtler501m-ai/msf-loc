package com.ktmmobile.msf.form.newchange.util;

import static com.ktmmobile.msf.system.common.constants.Constants.*;

public class AppformUtil {

    public static boolean isSuccessOP2(String rsltCd) {
        // '0000'뿐 아니라 '3060', '3070' 또한 정상 개통 포함
        return OSST_SUCCESS.equals(rsltCd) || OSST_ERROR_SEND_GUIDE.equals(rsltCd) || OSST_ERROR_SEND_OTA.equals(rsltCd);
    }
}