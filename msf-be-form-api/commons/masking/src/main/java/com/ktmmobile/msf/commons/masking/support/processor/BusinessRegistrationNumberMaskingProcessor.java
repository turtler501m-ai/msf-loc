package com.ktmmobile.msf.commons.masking.support.processor;

import com.ktmmobile.msf.commons.masking.domain.code.MaskingType;

/**
 * 사업자번호 마스킹 Processor
 */
public class BusinessRegistrationNumberMaskingProcessor implements MaskingProcessor {

    /** 숫자 기준 4자리 미만은 사업자번호로 판단하기 어려워 원문 유지 */
    private static final int MIN_MASKING_DIGIT_COUNT = 4;

    @Override
    public MaskingType type() {
        return MaskingType.BUSINESS_REGISTRATION_NUMBER;
    }

    @Override
    public String mask(String value) {
        String digits = MaskingProcessorUtils.onlyDigits(value);
        if (digits.length() < MIN_MASKING_DIGIT_COUNT) {
            return value;
        }
        return MaskingProcessorUtils.maskDigits(value, 3, 0);
    }
}
