package com.ktmmobile.msf.commons.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * INSERT/UPDATE/MERGE 시 Audit 컬럼을 동적으로 자동 삽입할지 여부를 결정합니다.
 * Mapper Interface 또는 Method 레벨에 사용할 수 있습니다.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AutoAuditing {

    boolean value() default true;
}
