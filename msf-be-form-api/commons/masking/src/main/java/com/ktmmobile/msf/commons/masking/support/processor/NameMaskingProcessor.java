package com.ktmmobile.msf.commons.masking.support.processor;

import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.masking.domain.code.MaskingType;

/**
 * 이름 마스킹 Processor
 */
public class NameMaskingProcessor implements MaskingProcessor {

    @Override
    public MaskingType type() {
        return MaskingType.NAME;
    }

    @Override
    public String mask(String value) {
        if (!StringUtils.hasText(value)) {
            return value;
        }

        int codePointCount = value.codePointCount(0, value.length());
        if (codePointCount == 1) {
            return String.valueOf(MaskingProcessorUtils.MASK);
        }
        if (codePointCount == 2) {
            return MaskingProcessorUtils.firstCodePoint(value) + MaskingProcessorUtils.MASK;
        }
        return MaskingProcessorUtils.firstCodePoint(value)
            + String.valueOf(MaskingProcessorUtils.MASK).repeat(codePointCount - 2)
            + MaskingProcessorUtils.lastCodePoint(value);
    }
}
