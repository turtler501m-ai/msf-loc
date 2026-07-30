package com.ktmmobile.msf.commons.logincore.domain.dto;

import java.util.Map;

public final class LoginAttributes {

    /**
     * 유틸리티 클래스 생성 방지
     */
    private LoginAttributes() {
    }

    /**
     * 문자열 속성 조회
     *
     * @param attributes 속성 Map
     * @param name 속성명
     * @return 문자열 속성 값
     */
    public static String getString(Map<String, Object> attributes, String name) {
        Object value = value(attributes, name);
        return value == null ? null : String.valueOf(value);
    }

    /**
     * Boolean 속성 조회
     *
     * @param attributes 속성 Map
     * @param name 속성명
     * @return Boolean 속성 값
     */
    public static Boolean getBoolean(Map<String, Object> attributes, String name) {
        Object value = value(attributes, name);
        if (value instanceof Boolean booleanValue) {
            return booleanValue;
        }
        return value == null ? null : Boolean.valueOf(String.valueOf(value));
    }

    /**
     * 속성 원본 값 조회
     *
     * @param attributes 속성 Map
     * @param name 속성명
     * @return 속성 원본 값
     */
    private static Object value(Map<String, Object> attributes, String name) {
        if (attributes == null || name == null) {
            return null;
        }
        return attributes.get(name);
    }
}
