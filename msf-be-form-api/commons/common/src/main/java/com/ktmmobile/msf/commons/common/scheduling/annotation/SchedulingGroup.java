package com.ktmmobile.msf.commons.common.scheduling.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * 스케줄러 클래스에 실행 제어 그룹 지정
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SchedulingGroup {

    @AliasFor("groupNames")
    String[] value() default {};

    @AliasFor("value")
    String[] groupNames() default {};
}
