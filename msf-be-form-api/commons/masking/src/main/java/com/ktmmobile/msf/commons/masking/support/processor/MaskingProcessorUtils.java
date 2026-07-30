package com.ktmmobile.msf.commons.masking.support.processor;

import java.util.regex.Pattern;

/**
 * 타입별 Processor가 공유하는 문자열 마스킹 보조 함수
 */
public final class MaskingProcessorUtils {

    static final char MASK = '*';

    private static final Pattern NON_DIGIT = Pattern.compile("\\D");

    private MaskingProcessorUtils() {
    }

    /** 숫자만 노출 여부 판단, 하이픈 같은 비숫자 문자는 원문 형식 유지 */
    static String maskDigits(String value, int visibleDigitPrefixLength, int visibleDigitSuffixLength) {
        int[] codePoints = value.codePoints().toArray();
        int digitCount = countDigits(codePoints);
        int digitIndex = 0;
        StringBuilder masked = new StringBuilder(value.length());
        for (int codePoint: codePoints) {
            if (Character.isDigit(codePoint)) {
                int remainingDigits = digitCount - digitIndex;
                if (digitIndex < visibleDigitPrefixLength || remainingDigits <= visibleDigitSuffixLength) {
                    masked.appendCodePoint(codePoint);
                } else {
                    masked.append(MASK);
                }
                digitIndex++;
            } else {
                masked.appendCodePoint(codePoint);
            }
        }
        return masked.toString();
    }

    static String maskMiddle(String value, int visiblePrefixLength, int visibleSuffixLength) {
        int codePointCount = value.codePointCount(0, value.length());
        if (codePointCount <= visiblePrefixLength + visibleSuffixLength) {
            return String.valueOf(MASK).repeat(codePointCount);
        }
        String prefix = value.substring(0, value.offsetByCodePoints(0, visiblePrefixLength));
        String suffix = value.substring(value.offsetByCodePoints(0, codePointCount - visibleSuffixLength));
        return prefix + String.valueOf(MASK).repeat(codePointCount - visiblePrefixLength - visibleSuffixLength) + suffix;
    }

    public static String maskAll(String value) {
        return maskAll(value, value.length());
    }

    public static String maskAll(String value, int maxLength) {
        int maskedLength = Math.min(value.codePointCount(0, value.length()), maxLength);
        return String.valueOf(MASK).repeat(maskedLength);
    }

    static String onlyDigits(String value) {
        return NON_DIGIT.matcher(value).replaceAll("");
    }

    static String firstCodePoint(String value) {
        return value.substring(0, value.offsetByCodePoints(0, 1));
    }

    static String lastCodePoint(String value) {
        return value.substring(value.offsetByCodePoints(0, value.codePointCount(0, value.length()) - 1));
    }

    private static int countDigits(int[] codePoints) {
        int count = 0;
        for (int codePoint: codePoints) {
            if (Character.isDigit(codePoint)) {
                count++;
            }
        }
        return count;
    }
}
