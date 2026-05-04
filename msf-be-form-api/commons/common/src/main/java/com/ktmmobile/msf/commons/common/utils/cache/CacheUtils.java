package com.ktmmobile.msf.commons.common.utils.cache;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.ktmmobile.msf.commons.common.utils.env.EnvironmentUtils;
import com.ktmmobile.msf.commons.common.utils.env.SpringCustomProperties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CacheUtils {

    private static SpringCustomProperties springCustomProperties;

    static void initialize(SpringCustomProperties springCustomProperties) {
        CacheUtils.springCustomProperties = springCustomProperties;
    }

    public static String getCachePrefix() {
        String appName = springCustomProperties.applicationNameAbbreviated();
        if (EnvironmentUtils.isLocal()) {
            return EnvironmentUtils.getLocalProfileCode() + ":" + appName + ":";
        }
        return appName + ":";
    }
}
