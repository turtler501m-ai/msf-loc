package com.ktmmobile.msf.domains.policy.password.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import com.ktmmobile.msf.domains.policy.password.vo.Password;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {
    ValidPasswordStringValidator.class,
    ValidPasswordValidator.class
})
public @interface ValidPassword {

    String message() default Password.INVALID_FORMAT_MESSAGE;

    String requiredMessage() default Password.REQUIRED_MESSAGE;

    String invalidFormatMessage() default Password.INVALID_FORMAT_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean allowNull() default false;
}
