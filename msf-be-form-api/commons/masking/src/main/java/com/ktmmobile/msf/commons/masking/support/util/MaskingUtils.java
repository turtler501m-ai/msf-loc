package com.ktmmobile.msf.commons.masking.support.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.ktmmobile.msf.commons.masking.domain.code.MaskingType;
import com.ktmmobile.msf.commons.masking.support.processor.MaskingProcessorRegistry;
import com.ktmmobile.msf.commons.masking.support.processor.MaskingProcessorUtils;

/**
 * 마스킹 유틸리티
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MaskingUtils {

    private static final MaskingProcessorRegistry REGISTRY = new MaskingProcessorRegistry();

    /** 문자열 자릿수만큼 전체 마스킹 */
    public static String mask(String value) {
        return value == null ? null : mask(value, value.length());
    }

    /** 문자열 자릿수만큼 전체 마스킹 (최대 자릿수 제한) */
    public static String mask(String value, int maxLength) {
        if (value == null) {
            return null;
        }
        return MaskingProcessorUtils.maskAll(value, maxLength);
    }

    /** 지정한 마스킹 타입 정책으로 마스킹 */
    public static String mask(String value, MaskingType type) {
        return REGISTRY.mask(value, type);
    }
}
