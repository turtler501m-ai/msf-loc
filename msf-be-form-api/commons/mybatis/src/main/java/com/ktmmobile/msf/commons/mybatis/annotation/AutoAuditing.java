package com.ktmmobile.msf.commons.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * INSERT/UPDATE/MERGE 시 Audit 컬럼 동적 자동 삽입 여부 결정
 * <p>Mapper Interface 또는 Method 레벨 사용 가능
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AutoAuditing {

    /**
     * 자동 Auditing 활성화 여부
     */
    @AliasFor("enabled")
    boolean value() default true;

    /**
     * 자동 Auditing 활성화 여부
     */
    @AliasFor("value")
    boolean enabled() default true;

    /**
     * 수정 Auditing 컬럼 포함 여부
     */
    boolean includeAmendColumns() default true;

    /**
     * 지정 Modifier 강제 적용 여부
     */
    boolean forceApply() default false;

    /**
     * 사용자 정의 Modifier
     */
    String modifier() default "";

    /**
     * 요청 없는 경우 대체 Client IP 사용 여부
     */
    boolean fallbackClientIp() default false;
}
