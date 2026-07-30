package com.ktmmobile.msf.commons.masking.support.processor;

import com.ktmmobile.msf.commons.masking.domain.code.MaskingType;

/**
 * 법인번호 마스킹 Processor
 */
public class CorporateRegistrationNumberMaskingProcessor implements MaskingProcessor {

    /** 숫자 기준 7자리 미만은 법인번호로 판단하기 어려워 원문 유지 */
    private static final int MIN_MASKING_DIGIT_COUNT = 7;

    @Override
    public MaskingType type() {
        return MaskingType.CORPORATE_REGISTRATION_NUMBER;
    }

    @Override
    public String mask(String value) {
        String digits = MaskingProcessorUtils.onlyDigits(value);
        if (digits.length() < MIN_MASKING_DIGIT_COUNT) {
            return value;
        }
        return MaskingProcessorUtils.maskDigits(value, 6, 0);
    }
}
