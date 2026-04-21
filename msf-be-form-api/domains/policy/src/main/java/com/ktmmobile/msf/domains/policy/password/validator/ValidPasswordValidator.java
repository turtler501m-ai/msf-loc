package com.ktmmobile.msf.domains.policy.password.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.ktmmobile.msf.domains.policy.password.vo.Password;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, Password> {

    private boolean allowNull;
    private String requiredMessage;

    @Override
    public void initialize(ValidPassword annotation) {
        allowNull = annotation.allowNull();
        requiredMessage = annotation.requiredMessage();
    }

    @Override
    public boolean isValid(Password value, ConstraintValidatorContext context) {
        if (value == null) {
            if (!allowNull) {
                addViolation(context, requiredMessage);
            }
            return allowNull;
        }
        return true;
    }

    private void addViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
            .addConstraintViolation();
    }
}
