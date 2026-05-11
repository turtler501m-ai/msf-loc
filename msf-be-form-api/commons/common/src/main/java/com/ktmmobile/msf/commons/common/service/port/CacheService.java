package com.ktmmobile.msf.commons.common.service.port;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CacheService<T> {

    void setValue(String key, T value);

    void setValue(String key, T value, Duration timeout);

    void setValue(String key, T value, LocalDate expireDate);

    boolean setValueIfAbsent(String key, T value);

    boolean setValueIfAbsent(String key, T value, Duration timeout);

    void setValues(String key, Map<String, T> values);

    void setValues(String key, Map<String, T> values, Duration timeout);

    void setValue(String key, String hashKey, T value);

    void setValue(String key, String hashKey, T value, Duration timeout);

    void replaceValues(String key, Map<String, T> values);

    void replaceValues(String key, Map<String, T> values, Duration timeout);

    T getValue(String key);

    T getValue(String key, String hashKey);

    Map<String, T> getEntries(String key);

    List<T> getValues(String pattern);

    List<T> getValues(String pattern, int limit);

    boolean hasKey(String key);

    boolean hasKey(String key, String hashKey);

    void delete(String key);

    boolean deleteIfValueEquals(String key, T value);

    void delete(String key, String hashKey);

    void deleteAll(List<String> keys);

    long increment(String key);

    long increment(String key, Duration timeout);

    long increment(String key, long delta);

    long increment(String key, long delta, Duration timeout);

    long increment(String key, LocalDate expireDate);

    long increment(String key, long delta, LocalDate expireDate);

    long decrement(String key);

    long decrement(String key, long delta);
}
