package com.ktmmobile.msf.commons.masking.support.processor;

import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.masking.domain.code.MaskingType;

/**
 * 이메일 주소 마스킹 processor
 */
public class EmailMaskingProcessor implements MaskingProcessor {

    @Override
    public MaskingType type() {
        return MaskingType.EMAIL;
    }

    @Override
    public String mask(String value) {
        if (!StringUtils.hasText(value)) {
            return value;
        }

        int atIndex = value.indexOf('@');
        if (atIndex <= 0) {
            return MaskingTextUtils.maskMiddle(value, 1, 0);
        }

        String localPart = value.substring(0, atIndex);
        String domainPart = value.substring(atIndex);
        return maskLocalPart(localPart) + domainPart;
    }

    private String maskLocalPart(String localPart) {
        int codePointCount = localPart.codePointCount(0, localPart.length());
        if (codePointCount == 1) {
            return String.valueOf(MaskingTextUtils.MASK);
        }
        return MaskingTextUtils.firstCodePoint(localPart) + String.valueOf(MaskingTextUtils.MASK).repeat(codePointCount - 1);
    }
}
