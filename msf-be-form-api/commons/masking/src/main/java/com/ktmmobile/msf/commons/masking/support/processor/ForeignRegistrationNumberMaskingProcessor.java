package com.ktmmobile.msf.commons.masking.support.processor;

import com.ktmmobile.msf.commons.masking.domain.code.MaskingType;

/**
 * 외국인등록번호 마스킹 Processor
 */
public class ForeignRegistrationNumberMaskingProcessor implements MaskingProcessor {

    @Override
    public MaskingType type() {
        return MaskingType.FOREIGN_REGISTRATION_NUMBER;
    }

    @Override
    public String mask(String value) {
        return ResidentRegistrationNumberMaskingProcessor.maskRegistrationNumber(value);
    }
}
