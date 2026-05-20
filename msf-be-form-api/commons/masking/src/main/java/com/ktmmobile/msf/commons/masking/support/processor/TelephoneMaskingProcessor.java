package com.ktmmobile.msf.commons.masking.support.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ktmmobile.msf.commons.masking.domain.code.MaskingType;

/**
 * 전화번호 마스킹 Processor
 */
public class TelephoneMaskingProcessor implements MaskingProcessor {

    /** 숫자 기준 8자리 미만은 전화번호로 판단하기 어려워 원문 유지 */
    private static final int MIN_MASKING_DIGIT_COUNT = 8;

    /** 구분자가 있는 3그룹 전화번호 형식 */
    private static final Pattern FORMATTED_PHONE_NUMBER = Pattern.compile("^(\\D*\\d+\\D+)(\\d+)(\\D+\\d+\\D*)$");

    @Override
    public MaskingType type() {
        return MaskingType.TELEPHONE;
    }

    @Override
    public String mask(String value) {
        return maskPhoneNumber(value, MIN_MASKING_DIGIT_COUNT, true);
    }

    static String maskPhoneNumber(String value, int minMaskingDigitCount) {
        return maskPhoneNumber(value, minMaskingDigitCount, false);
    }

    static String maskPhoneNumber(String value, int minMaskingDigitCount, boolean fixedFormattedMiddleMaskLength) {
        String digits = MaskingTextUtils.onlyDigits(value);
        if (digits.length() < minMaskingDigitCount) {
            return value;
        }

        String formattedPhoneNumber = maskFormattedPhoneNumber(value, fixedFormattedMiddleMaskLength);
        if (formattedPhoneNumber != null) {
            return formattedPhoneNumber;
        }

        PhoneMaskingRule maskingRule = resolveMaskingRule(value, digits);
        return MaskingTextUtils.maskDigits(value, maskingRule.prefixLength(), maskingRule.suffixLength());
    }

    /** 구분자가 있는 전화번호는 중간 번호 그룹을 고정 길이로 마스킹 */
    private static String maskFormattedPhoneNumber(String value, boolean fixedFormattedMiddleMaskLength) {
        Matcher matcher = FORMATTED_PHONE_NUMBER.matcher(value);
        if (!matcher.matches()) {
            return null;
        }
        String mask = String.valueOf(MaskingTextUtils.MASK).repeat(maskLength(matcher.group(2), fixedFormattedMiddleMaskLength));
        return matcher.group(1) + mask + matcher.group(3);
    }

    /** 전화번호 정책에 따라 중간 번호 그룹 마스킹 길이 계산 */
    private static int maskLength(String middleDigitGroup, boolean fixedFormattedMiddleMaskLength) {
        if (fixedFormattedMiddleMaskLength) {
            return 4;
        }
        return middleDigitGroup.length();
    }

    private static PhoneMaskingRule resolveMaskingRule(String value, String digits) {
        int leadingDigitGroupLength = leadingDigitGroupLength(value);
        if (leadingDigitGroupLength >= 2) {
            // 하이픈이 있는 번호는 첫 숫자 그룹을 식별부로 유지
            return new PhoneMaskingRule(leadingDigitGroupLength, resolveSuffixLength(digits, leadingDigitGroupLength));
        }

        if (digits.startsWith("02")) {
            // 서울 지역번호는 2자리 식별부 유지
            return new PhoneMaskingRule(2, 4);
        }
        if (digits.startsWith("050") && digits.length() >= 11) {
            // 050 개인번호 계열은 050X 형태의 4자리 식별부 유지
            return new PhoneMaskingRule(4, 4);
        }
        if (isRepresentativeNumber(digits)) {
            // 14XX, 15XX, 16XX, 18XX 대표번호는 뒤 4자리 전체 마스킹
            return new PhoneMaskingRule(4, 0);
        }
        // 그 외 지역번호/인터넷전화/착신과금 번호는 최대 3자리 식별부 유지
        return new PhoneMaskingRule(Math.clamp(digits.length() - 4L, 0, 3), 4);
    }

    /** 식별부와 가입자번호만 있는 대표번호 형태에서는 suffix 노출 없이 뒤 번호 전체 마스킹 */
    private static int resolveSuffixLength(String digits, int prefixLength) {
        if (digits.length() == prefixLength + 4) {
            return 0;
        }
        return 4;
    }

    /** 하이픈 등 구분자가 있는 경우 첫 구분자 앞 숫자 그룹 길이 추출 */
    private static int leadingDigitGroupLength(String value) {
        int count = 0;
        for (int codePoint : value.codePoints().toArray()) {
            if (!Character.isDigit(codePoint)) {
                break;
            }
            count++;
        }
        return count == value.codePointCount(0, value.length()) ? 0 : count;
    }

    /** 국내 대표번호 계열 여부 */
    private static boolean isRepresentativeNumber(String digits) {
        return digits.length() == 8
            && (digits.startsWith("14")
                || digits.startsWith("15")
                || digits.startsWith("16")
                || digits.startsWith("18"));
    }

    private record PhoneMaskingRule(
        int prefixLength,
        int suffixLength
    ) {
    }
}
