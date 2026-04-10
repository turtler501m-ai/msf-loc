package com.ktmmobile.msf.commons.common.commonenum.serializer;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ValueDeserializer;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

public class CommonEnumDeserializer<T extends CommonEnum> extends ValueDeserializer<T> {

    private final Class<T> type;

    public CommonEnumDeserializer(Class<T> type) {
        this.type = type;
    }

    @Override
    public ValueDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        if (property != null) {
            @SuppressWarnings("unchecked")
            Class<T> rawClass = (Class<T>) property.getType().getRawClass();
            return new CommonEnumDeserializer<>(rawClass);
        }

        JavaType contextualType = ctxt.getContextualType();
        if (contextualType == null) {
            return this;
        }

        @SuppressWarnings("unchecked")
        Class<T> rawClass = (Class<T>) contextualType.getRawClass();
        return new CommonEnumDeserializer<>(rawClass);
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
        String code = p.getValueAsString();
        return CommonEnum.valueOfCode(type, code);
    }
}
