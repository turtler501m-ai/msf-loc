package com.ktmmobile.msf.commons.masking.support.processor;

import com.ktmmobile.msf.commons.masking.domain.code.MaskingType;

/**
 * 비밀번호 마스킹 Processor
 */
public class PasswordMaskingProcessor implements MaskingProcessor {

    /** 비밀번호 길이 노출 방지를 위한 고정 마스킹 길이 */
    private static final int MASKING_LENGTH = 8;

    @Override
    public MaskingType type() {
        return MaskingType.PASSWORD;
    }

    @Override
    public String mask(String value) {
        return String.valueOf(MaskingProcessorUtils.MASK).repeat(MASKING_LENGTH);
    }
}
