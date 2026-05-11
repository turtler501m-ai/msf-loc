package com.ktmmobile.msf.commons.common.utils.cache;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.ktmmobile.msf.commons.common.utils.env.EnvironmentUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CacheUtils {

    public static String getCachePrefix() {
        if (EnvironmentUtils.isLocal()) {
            return EnvironmentUtils.getLocalProfileCode() + ":";
        }
        return "";
    }
}
