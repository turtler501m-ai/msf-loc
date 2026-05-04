package com.ktmmobile.msf.commons.logincore.domain.dto;

import java.util.Map;

public final class LoginAttributes {

    private LoginAttributes() {
    }

    public static String getString(Map<String, Object> attributes, String name) {
        Object value = value(attributes, name);
        return value == null ? null : String.valueOf(value);
    }

    public static Boolean getBoolean(Map<String, Object> attributes, String name) {
        Object value = value(attributes, name);
        if (value instanceof Boolean booleanValue) {
            return booleanValue;
        }
        return value == null ? null : Boolean.valueOf(String.valueOf(value));
    }

    private static Object value(Map<String, Object> attributes, String name) {
        if (attributes == null || name == null) {
            return null;
        }
        return attributes.get(name);
    }
}
