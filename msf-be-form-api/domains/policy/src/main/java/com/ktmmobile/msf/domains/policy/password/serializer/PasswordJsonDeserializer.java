package com.ktmmobile.msf.domains.policy.password.serializer;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ValueDeserializer;

import com.ktmmobile.msf.commons.common.exception.InvalidValueException;
import com.ktmmobile.msf.domains.policy.password.validator.ValidPassword;
import com.ktmmobile.msf.domains.policy.password.vo.Password;

public class PasswordJsonDeserializer extends ValueDeserializer<Password> {

    private final String requiredMessage;
    private final String invalidFormatMessage;

    public PasswordJsonDeserializer() {
        this(Password.REQUIRED_MESSAGE, Password.INVALID_FORMAT_MESSAGE);
    }

    private PasswordJsonDeserializer(String requiredMessage, String invalidFormatMessage) {
        this.requiredMessage = requiredMessage;
        this.invalidFormatMessage = invalidFormatMessage;
    }

    @Override
    public ValueDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        ValidPassword annotation = findAnnotation(ctxt, property);
        if (annotation == null) {
            return this;
        }
        return new PasswordJsonDeserializer(annotation.requiredMessage(), annotation.invalidFormatMessage());
    }

    @Override
    public Password deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
        String value = p.getValueAsString();
        if (!Password.hasText(value)) {
            throw new InvalidValueException(requiredMessage);
        }
        if (!Password.isValidFormat(value)) {
            throw new InvalidValueException(invalidFormatMessage);
        }
        return new Password(value);
    }

    private ValidPassword findAnnotation(DeserializationContext ctxt, BeanProperty property) {
        if (property != null) {
            ValidPassword annotation = property.getAnnotation(ValidPassword.class);
            if (annotation != null) {
                return annotation;
            }
            annotation = property.getContextAnnotation(ValidPassword.class);
            if (annotation != null) {
                return annotation;
            }
        }

        JavaType contextualType = ctxt.getContextualType();
        if (contextualType != null) {
            return contextualType.getRawClass().getAnnotation(ValidPassword.class);
        }
        return null;
    }
}
