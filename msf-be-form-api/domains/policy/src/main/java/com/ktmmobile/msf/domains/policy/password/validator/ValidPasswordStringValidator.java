package com.ktmmobile.msf.domains.policy.password.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.ktmmobile.msf.domains.policy.password.vo.Password;

public class ValidPasswordStringValidator implements ConstraintValidator<ValidPassword, String> {

    private boolean allowNull;
    private String requiredMessage;
    private String invalidFormatMessage;

    @Override
    public void initialize(ValidPassword annotation) {
        allowNull = annotation.allowNull();
        requiredMessage = annotation.requiredMessage();
        invalidFormatMessage = annotation.invalidFormatMessage();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            if (!allowNull) {
                addViolation(context, requiredMessage);
            }
            return allowNull;
        }
        if (!Password.hasText(value)) {
            addViolation(context, requiredMessage);
            return false;
        }
        if (!Password.isValidFormat(value)) {
            addViolation(context, invalidFormatMessage);
            return false;
        }
        return true;
    }

    private void addViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
            .addConstraintViolation();
    }
}
