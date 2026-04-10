package com.ktmmobile.msf.commons.common.commonenum.core;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public interface CommonEnum {

    @JsonValue
    String getCode();

    String getTitle();


    static <T extends CommonEnum> T valueOfCode(Class<T> type, String code) {
        T invalidValue = null;
        for (T value: type.getEnumConstants()) {
            if (value.getCode().equals(code)) {
                return value;
            }
            if (!value.isValid()) {
                invalidValue = value;
            }
        }
        return invalidValue;
    }

    static <T extends CommonEnum> T valueOfCode(Class<T> type, String code, T invalidValue) {
        for (T value: type.getEnumConstants()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return invalidValue;
    }

    default boolean isValid() {
        return !this.getCode().equals(CommonEnumConstant.UNDEFINED_CODE);
    }

    static List<String> getAllCodes(Class<? extends CommonEnum> type) {
        return Arrays.stream(type.getEnumConstants())
            .filter(CommonEnum::isValid)
            .map(CommonEnum::getCode)
            .toList();
    }

    static List<String> getAllDescriptions(Class<? extends CommonEnum> type) {
        return Arrays.stream(type.getEnumConstants())
            .filter(CommonEnum::isValid)
            .map(CommonEnum::getDescription)
            .toList();
    }

    static List<String> getDescriptions(CommonEnum... values) {
        return getDescriptions(List.of(values));
    }

    static List<String> getDescriptions(List<? extends CommonEnum> values) {
        return values.stream()
            .map(CommonEnum::getDescription)
            .toList();
    }

    static String getDescription(CommonEnum value) {
        return String.format("%s:%s", value.getCode(), value.getTitle());
    }
}
