package com.ktmmobile.msf.commons.common.commonenum.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

public class CommonEnumConverterFactory<R extends CommonEnum> implements ConverterFactory<String, R> {

    @Override
    public <T extends R> Converter<String, T> getConverter(Class<T> targetType) {
        if (CommonEnum.class.isAssignableFrom(targetType)) {
            return new CommonEnumConverter<>(targetType);
        }
        return null;
    }

    private record CommonEnumConverter<T extends CommonEnum>(Class<T> type) implements Converter<String, T> {

        @Override
        public T convert(String code) {
            return CommonEnum.valueOfCode(type, code);
        }
    }
}
