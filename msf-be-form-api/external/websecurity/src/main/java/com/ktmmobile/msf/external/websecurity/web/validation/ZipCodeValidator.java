package com.ktmmobile.msf.external.websecurity.web.validation;

import java.util.regex.Pattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

public class ZipCodeValidator implements ConstraintValidator<ZipCode, String> {

    private static final Pattern ZIPCODE_PATTERN = Pattern.compile("^\\d{1,6}$");
    private boolean allowNull;

    @Override
    public void initialize(ZipCode annotation) {
        allowNull = annotation.allowNull();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(value)) {
            return allowNull;
        }
        return ZIPCODE_PATTERN.matcher(value).matches();
    }
}
