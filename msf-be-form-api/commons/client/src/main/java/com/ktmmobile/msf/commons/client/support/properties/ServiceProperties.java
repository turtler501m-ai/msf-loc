package com.ktmmobile.msf.commons.client.support.properties;

import java.util.Map;

import org.springframework.util.StringUtils;

public record ServiceProperties(
    String baseUrl,
    Map<String, String> properties
) {

    public ServiceProperties {
        properties = properties == null ? Map.of() : Map.copyOf(properties);
    }

    public String property(String key) {
        String value = properties.get(key);
        if (value != null) {
            return value;
        }

        value = properties.get(toKebabCase(key));
        if (value != null) {
            return value;
        }

        return properties.get(toCamelCase(key));
    }

    private static String toKebabCase(String key) {
        if (!StringUtils.hasText(key)) {
            return key;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            if (Character.isUpperCase(ch)) {
                if (i > 0) {
                    builder.append('-');
                }
                builder.append(Character.toLowerCase(ch));
            } else {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    private static String toCamelCase(String key) {
        if (!StringUtils.hasText(key)) {
            return key;
        }

        StringBuilder builder = new StringBuilder();
        boolean upperCaseNext = false;
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            if (ch == '-' || ch == '_' || ch == ' ') {
                upperCaseNext = true;
                continue;
            }

            if (upperCaseNext) {
                builder.append(Character.toUpperCase(ch));
                upperCaseNext = false;
            } else {
                builder.append(ch);
            }
        }
        return builder.toString();
    }
}
