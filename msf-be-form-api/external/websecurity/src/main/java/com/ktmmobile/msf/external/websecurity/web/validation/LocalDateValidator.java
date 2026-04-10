package com.ktmmobile.msf.external.websecurity.web.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.ktmmobile.msf.commons.common.data.type.LocalDateCondition;
import com.ktmmobile.msf.commons.common.utils.DateTimeUtils;

public class LocalDateValidator implements ConstraintValidator<ValidLocalDate, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeUtils.DEFAULT_DATE;
    private LocalDateCondition condition;

    @Override
    public void initialize(ValidLocalDate annotation) {
        condition = annotation.condition();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            if (value == null || value.isEmpty()) {
                return true;
            }

            if (!isValid(LocalDate.parse(value, FORMATTER))) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(getValidationMessage()).addConstraintViolation();
                return false;
            }
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    private boolean isValid(LocalDate localDate) {
        LocalDate now = LocalDate.now();
        if (condition == LocalDateCondition.PAST) {
            return localDate.isBefore(now);
        }
        if (condition == LocalDateCondition.PAST_OR_PRESENT) {
            return localDate.isBefore(now) || localDate.isEqual(now);
        }
        if (condition == LocalDateCondition.FUTURE) {
            return localDate.isAfter(now);
        }
        if (condition == LocalDateCondition.FUTURE_OR_PRESENT) {
            return localDate.isAfter(now) || localDate.isEqual(now);
        }
        return true;
    }

    private String getValidationMessage() {
        if (condition == LocalDateCondition.PAST) {
            return LocalDateCondition.PAST.getMessage();
        }
        if (condition == LocalDateCondition.PAST_OR_PRESENT) {
            return LocalDateCondition.PAST_OR_PRESENT.getMessage();
        }
        if (condition == LocalDateCondition.FUTURE) {
            return LocalDateCondition.FUTURE.getMessage();
        }
        return LocalDateCondition.FUTURE_OR_PRESENT.getMessage();
    }

}
