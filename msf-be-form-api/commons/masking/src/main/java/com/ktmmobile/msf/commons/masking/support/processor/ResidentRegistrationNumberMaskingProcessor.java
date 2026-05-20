package com.ktmmobile.msf.commons.masking.support.processor;

import com.ktmmobile.msf.commons.masking.domain.code.MaskingType;

/**
 * 주민등록번호 마스킹 Processor
 */
public class ResidentRegistrationNumberMaskingProcessor implements MaskingProcessor {

    /** 숫자 기준 8자리 미만은 주민등록번호로 판단하기 어려워 원문 유지 */
    private static final int MIN_MASKING_DIGIT_COUNT = 8;

    @Override
    public MaskingType type() {
        return MaskingType.RESIDENT_REGISTRATION_NUMBER;
    }

    @Override
    public String mask(String value) {
        return maskRegistrationNumber(value);
    }

    static String maskRegistrationNumber(String value) {
        String digits = MaskingTextUtils.onlyDigits(value);
        if (digits.length() < MIN_MASKING_DIGIT_COUNT) {
            return value;
        }
        return MaskingTextUtils.maskDigits(value, 7, 0);
    }
}
