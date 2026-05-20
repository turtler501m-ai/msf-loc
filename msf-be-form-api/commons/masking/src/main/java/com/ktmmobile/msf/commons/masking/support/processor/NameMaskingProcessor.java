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
            return String.valueOf(MaskingTextUtils.MASK);
        }
        if (codePointCount == 2) {
            return MaskingTextUtils.firstCodePoint(value) + MaskingTextUtils.MASK;
        }
        return MaskingTextUtils.firstCodePoint(value)
            + String.valueOf(MaskingTextUtils.MASK).repeat(codePointCount - 2)
            + MaskingTextUtils.lastCodePoint(value);
    }
}
