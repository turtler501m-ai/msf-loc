package com.ktmmobile.msf.external.websecurity.web.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import com.ktmmobile.msf.commons.common.data.type.LocalDateCondition;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LocalDateValidator.class)
public @interface ValidLocalDate {

    String message() default "날짜 형식에 맞지 않습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean allowNull() default false;

    LocalDateCondition condition() default LocalDateCondition.VALID;

}
