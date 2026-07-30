package com.ktmmobile.msf.commons.crypto.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;

/**
 * MyBatis INSERT/UPDATE 직전 암호화하고 SELECT 직후 복호화할 문자열 필드
 */
@Target({ElementType.FIELD, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
public @interface Encrypted {

    /** 필드 암복호화 알고리즘 */
    @AliasFor("algorithm")
    FieldCryptoAlgorithm value() default FieldCryptoAlgorithm.AES_GCM;

    /** 필드 암복호화 알고리즘 */
    @AliasFor("value")
    FieldCryptoAlgorithm algorithm() default FieldCryptoAlgorithm.AES_GCM;
}
