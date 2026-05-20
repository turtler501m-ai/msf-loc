package com.ktmmobile.msf.commons.masking.support.processor;

import com.ktmmobile.msf.commons.masking.domain.code.MaskingType;

/**
 * MaskingType 하나에 대응하는 마스킹 정책 구현체
 */
public interface MaskingProcessor {

    /** Processor 처리 대상 마스킹 타입 */
    MaskingType type();

    /** 원본 문자열을 타입별 정책에 따라 마스킹 */
    String mask(String value);
}
