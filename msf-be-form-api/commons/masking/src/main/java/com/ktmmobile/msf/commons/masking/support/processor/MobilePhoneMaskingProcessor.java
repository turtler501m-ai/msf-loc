package com.ktmmobile.msf.commons.masking.support.processor;

import com.ktmmobile.msf.commons.masking.domain.code.MaskingType;

/**
 * 휴대폰번호 마스킹 Processor
 */
public class MobilePhoneMaskingProcessor implements MaskingProcessor {

    /** 숫자 기준 8자리 미만은 휴대폰번호로 판단하기 어려워 원문 유지 */
    private static final int MIN_MASKING_DIGIT_COUNT = 8;

    @Override
    public MaskingType type() {
        return MaskingType.MOBILE_PHONE;
    }

    @Override
    public String mask(String value) {
        return TelephoneMaskingProcessor.maskPhoneNumber(value, MIN_MASKING_DIGIT_COUNT);
    }
}
