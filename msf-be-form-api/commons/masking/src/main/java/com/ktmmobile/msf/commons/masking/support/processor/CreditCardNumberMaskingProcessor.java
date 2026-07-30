package com.ktmmobile.msf.commons.masking.support.processor;

import com.ktmmobile.msf.commons.masking.domain.code.MaskingType;

/**
 * 신용카드번호 마스킹 Processor
 */
public class CreditCardNumberMaskingProcessor implements MaskingProcessor {

    /** 카드번호 16자리 중 7번째부터 12번째 번호 마스킹 */
    private static final int VISIBLE_DIGIT_PREFIX_LENGTH = 6;

    /** 카드번호 16자리 중 마지막 4자리 노출 */
    private static final int VISIBLE_DIGIT_SUFFIX_LENGTH = 4;

    /** 숫자 기준 13자리 미만은 카드번호로 판단하기 어려워 원문 유지 */
    private static final int MIN_MASKING_DIGIT_COUNT = 10;

    @Override
    public MaskingType type() {
        return MaskingType.CREDIT_CARD_NUMBER;
    }

    @Override
    public String mask(String value) {
        String digits = MaskingProcessorUtils.onlyDigits(value);
        if (digits.length() < MIN_MASKING_DIGIT_COUNT) {
            return value;
        }
        return MaskingProcessorUtils.maskDigits(value, VISIBLE_DIGIT_PREFIX_LENGTH, VISIBLE_DIGIT_SUFFIX_LENGTH);
    }
}
