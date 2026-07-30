package com.ktmmobile.msf.commons.websecurity.web.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContentTypeUtils {

    private static final String CHARSET_PARAMETER_PREFIX = "charset=";

    /**
     * Content-Type charset 조회
     */
    public static Charset contentCharset(String contentType) {
        if (contentType != null) {
            for (String part: contentType.split(";")) {
                String trimmed = part.trim();
                if (trimmed.regionMatches(true, 0, CHARSET_PARAMETER_PREFIX, 0, CHARSET_PARAMETER_PREFIX.length())) {
                    return charsetOrDefault(trimmed.substring(CHARSET_PARAMETER_PREFIX.length()));
                }
            }
        }
        return StandardCharsets.UTF_8;
    }

    private static Charset charsetOrDefault(String charsetName) {
        try {
            return Charset.forName(charsetName);
        } catch (RuntimeException _) {
            return StandardCharsets.UTF_8;
        }
    }
}
