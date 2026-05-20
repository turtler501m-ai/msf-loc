package com.ktmmobile.msf.commons.masking.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ktmmobile.msf.commons.masking.domain.code.MaskingType;

/**
 * JSON 응답 직렬화 시 문자열 계열 필드 값을 지정한 타입의 정책으로 마스킹
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
public @interface Masked {

    /** 적용할 마스킹 정책 타입 */
    MaskingType type();
}
