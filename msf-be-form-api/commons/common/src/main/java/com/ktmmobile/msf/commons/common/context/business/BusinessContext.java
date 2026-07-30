package com.ktmmobile.msf.commons.common.context.business;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.util.StringUtils;

/**
 * Business Context 값 컬렉션
 */
public record BusinessContext(
    Map<BusinessContextKey, String> values
) {

    public BusinessContext {
        values = Map.copyOf(values);
    }

    /**
     * 빈 Business Context
     */
    public static BusinessContext empty() {
        return new BusinessContext(Map.of());
    }

    /**
     * Business Context 값 조회
     */
    public Optional<String> get(BusinessContextKey key) {
        return Optional.ofNullable(values.get(key))
            .filter(StringUtils::hasText);
    }

    /**
     * Business Context 값 저장
     */
    public BusinessContext put(BusinessContextKey key, String value) {
        if (!StringUtils.hasText(value)) {
            return remove(key);
        }

        Map<BusinessContextKey, String> copied = copyValues();
        copied.put(key, value);
        return new BusinessContext(copied);
    }

    /**
     * Business Context 값 제거
     */
    public BusinessContext remove(BusinessContextKey key) {
        if (!values.containsKey(key)) {
            return this;
        }

        Map<BusinessContextKey, String> copied = copyValues();
        copied.remove(key);
        return new BusinessContext(copied);
    }

    /**
     * Business Context 값 존재 여부
     */
    public boolean isEmpty() {
        return values.isEmpty();
    }

    /**
     * Business Context 값 복사
     */
    private Map<BusinessContextKey, String> copyValues() {
        Map<BusinessContextKey, String> copied = new EnumMap<>(BusinessContextKey.class);
        copied.putAll(values);
        return copied;
    }
}
