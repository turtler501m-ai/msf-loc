package com.ktmmobile.msf.commons.common.service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.common.service.port.CacheService;

@ConditionalOnProperty(prefix = "spring.data.redis", name = "enabled", havingValue = "false")
@Service
public class InMemoryCacheService<T> implements CacheService<T> {

    private final Map<String, Entry<T>> valueStore = new ConcurrentHashMap<>();
    private final Map<String, Entry<Map<String, T>>> hashStore = new ConcurrentHashMap<>();

    @Override
    public void setValue(String key, T value) {
        valueStore.put(key, Entry.value(value));
    }

    @Override
    public void setValue(String key, T value, Duration timeout) {
        valueStore.put(key, Entry.value(value, timeout));
    }

    @Override
    public void setValue(String key, T value, LocalDate expireDate) {
        valueStore.put(key, Entry.value(value, toExpireAt(expireDate)));
    }

    @Override
    public boolean setValueIfAbsent(String key, T value) {
        purgeIfExpired(key);
        return valueStore.putIfAbsent(key, Entry.value(value)) == null;
    }

    @Override
    public boolean setValueIfAbsent(String key, T value, Duration timeout) {
        purgeIfExpired(key);
        return valueStore.putIfAbsent(key, Entry.value(value, timeout)) == null;
    }

    @Override
    public void setValues(String key, Map<String, T> values) {
        hashStore.put(key, Entry.value(new ConcurrentHashMap<>(values)));
    }

    @Override
    public void setValues(String key, Map<String, T> values, Duration timeout) {
        hashStore.put(key, Entry.value(new ConcurrentHashMap<>(values), timeout));
    }

    @Override
    public void setValue(String key, String hashKey, T value) {
        getOrCreateHash(key, null).put(hashKey, value);
    }

    @Override
    public void setValue(String key, String hashKey, T value, Duration timeout) {
        getOrCreateHash(key, timeout).put(hashKey, value);
    }

    @Override
    public void replaceValues(String key, Map<String, T> values) {
        valueStore.remove(key);
        hashStore.put(key, Entry.value(new ConcurrentHashMap<>(values)));
    }

    @Override
    public void replaceValues(String key, Map<String, T> values, Duration timeout) {
        valueStore.remove(key);
        hashStore.put(key, Entry.value(new ConcurrentHashMap<>(values), timeout));
    }

    @Override
    public T getValue(String key) {
        Entry<T> entry = valueStore.get(key);
        if (entry == null || entry.isExpired()) {
            valueStore.remove(key);
            return null;
        }
        return entry.value();
    }

    @Override
    public T getValue(String key, String hashKey) {
        Map<String, T> values = getHash(key);
        return values == null ? null : values.get(hashKey);
    }

    @Override
    public Map<String, T> getEntries(String key) {
        Map<String, T> values = getHash(key);
        return values == null ? Map.of() : Map.copyOf(values);
    }

    @Override
    public List<T> getValues(String pattern) {
        return getValues(pattern, 0);
    }

    @Override
    public List<T> getValues(String pattern, int limit) {
        List<T> values = new ArrayList<>();
        for (String key: getRealKeys(pattern, limit)) {
            T value = getValueByRealKey(key);
            if (value != null) {
                values.add(value);
            }
        }
        return values;
    }

    @Override
    public boolean hasKey(String key) {
        return getValueByRealKey(key) != null || getHashByRealKey(key) != null;
    }

    @Override
    public boolean hasKey(String key, String hashKey) {
        Map<String, T> values = getHash(key);
        return values != null && values.containsKey(hashKey);
    }

    @Override
    public void delete(String key) {
        valueStore.remove(key);
        hashStore.remove(key);
    }

    @Override
    public boolean deleteIfValueEquals(String key, T value) {
        Entry<T> entry = valueStore.get(key);
        if (entry == null || entry.isExpired()) {
            valueStore.remove(key);
            return false;
        }
        if (!Objects.equals(entry.value(), value)) {
            return false;
        }
        valueStore.remove(key);
        return true;
    }

    @Override
    public void delete(String key, String hashKey) {
        Map<String, T> values = getHash(key);
        if (values != null) {
            values.remove(hashKey);
        }
    }

    @Override
    public void deleteAll(List<String> keys) {
        keys.forEach(this::delete);
    }

    @Override
    public long increment(String key) {
        return increment(key, 1L);
    }

    @Override
    public long increment(String key, Duration timeout) {
        return incrementWithTimeout(key, 1L, timeout);
    }

    @Override
    public long increment(String key, long delta) {
        return updateNumberValue(key, delta);
    }

    @Override
    public long increment(String key, long delta, Duration timeout) {
        return incrementWithTimeout(key, delta, timeout);
    }

    @Override
    public long increment(String key, LocalDate expireDate) {
        return incrementWithExpireDate(key, 1L, expireDate);
    }

    @Override
    public long increment(String key, long delta, LocalDate expireDate) {
        return incrementWithExpireDate(key, delta, expireDate);
    }

    @Override
    public long decrement(String key) {
        return decrement(key, 1L);
    }

    @Override
    public long decrement(String key, long delta) {
        return updateNumberValue(key, -delta);
    }

    private Set<String> getRealKeys(String pattern) {
        return getRealKeys(pattern, 0);
    }

    private Set<String> getRealKeys(String pattern, int limit) {
        purgeExpiredEntries();
        Pattern regex = Pattern.compile(toRegex(pattern));
        Map<String, Boolean> matchedKeys = new LinkedHashMap<>();
        addMatchedKeys(matchedKeys, valueStore.keySet(), regex, limit);
        addMatchedKeys(matchedKeys, hashStore.keySet(), regex, limit);
        return matchedKeys.keySet();
    }

    private long incrementWithTimeout(String key, long delta, Duration timeout) {
        long result = updateNumberValue(key, delta);
        if (result == delta) {
            expireValue(key, Instant.now().plus(timeout));
        }
        return result;
    }

    private long incrementWithExpireDate(String key, long delta, LocalDate expireDate) {
        long result = updateNumberValue(key, delta);
        if (result == delta) {
            expireValue(key, toExpireAt(expireDate));
        }
        return result;
    }

    private long updateNumberValue(String key, long delta) {
        purgeIfExpired(key);
        Entry<T> current = valueStore.get(key);
        long currentValue = current == null ? 0L : toLong(current.value());
        long updatedValue = currentValue + delta;
        Instant expiresAt = current == null ? null : current.expiresAt();
        valueStore.put(key, Entry.value(castNumber(updatedValue), expiresAt));
        return updatedValue;
    }

    private void expireValue(String key, Instant expiresAt) {
        Entry<T> current = valueStore.get(key);
        if (current != null) {
            valueStore.put(key, Entry.value(current.value(), expiresAt));
            return;
        }

        Entry<Map<String, T>> currentHash = hashStore.get(key);
        if (currentHash != null) {
            hashStore.put(key, Entry.value(currentHash.value(), expiresAt));
        }
    }

    private void purgeExpiredEntries() {
        purgeExpiredEntries(valueStore);
        purgeExpiredEntries(hashStore);
    }

    private <V> void purgeExpiredEntries(Map<String, Entry<V>> store) {
        store.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }

    private void purgeIfExpired(String realKey) {
        removeIfExpired(valueStore, realKey);
        removeIfExpired(hashStore, realKey);
    }

    private <V> void removeIfExpired(Map<String, Entry<V>> store, String realKey) {
        Entry<V> entry = store.get(realKey);
        if (entry != null && entry.isExpired()) {
            store.remove(realKey);
        }
    }

    private T getValueByRealKey(String realKey) {
        Entry<T> entry = valueStore.get(realKey);
        if (entry == null || entry.isExpired()) {
            valueStore.remove(realKey);
            return null;
        }
        return entry.value();
    }

    private Map<String, T> getHash(String key) {
        return getHashByRealKey(key);
    }

    private Map<String, T> getOrCreateHash(String key, Duration timeout) {
        purgeIfExpired(key);
        Entry<Map<String, T>> entry = hashStore.get(key);
        if (entry != null) {
            return entry.value();
        }

        Entry<Map<String, T>> createdEntry = timeout == null
            ? Entry.value(new ConcurrentHashMap<>())
            : Entry.value(new ConcurrentHashMap<>(), timeout);
        Entry<Map<String, T>> existingEntry = hashStore.putIfAbsent(key, createdEntry);
        return existingEntry == null ? createdEntry.value() : existingEntry.value();
    }

    private Map<String, T> getHashByRealKey(String realKey) {
        Entry<Map<String, T>> entry = hashStore.get(realKey);
        if (entry == null || entry.isExpired()) {
            hashStore.remove(realKey);
            return null;
        }
        return entry.value();
    }

    private void addMatchedKeys(Map<String, Boolean> matchedKeys, Collection<String> keys, Pattern regex, int limit) {
        for (String key: keys) {
            if (limit != 0 && matchedKeys.size() >= limit) {
                return;
            }
            if (regex.matcher(key).matches()) {
                matchedKeys.put(key, Boolean.TRUE);
            }
        }
    }

    private String toRegex(String pattern) {
        StringBuilder builder = new StringBuilder("^");
        for (char c: pattern.toCharArray()) {
            if (c == '*') {
                builder.append(".*");
            } else {
                builder.append(Pattern.quote(String.valueOf(c)));
            }
        }
        builder.append("$");
        return builder.toString();
    }

    private Instant toExpireAt(LocalDate expireDate) {
        return expireDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

    @SuppressWarnings("unchecked")
    private T castNumber(long value) {
        return (T) Long.valueOf(value);
    }

    private long toLong(Object value) {
        if (value instanceof Number number) {
            return number.longValue();
        }
        throw new IllegalStateException("Stored value is not numeric: " + value);
    }

    
    private record Entry<V>(V value, Instant expiresAt) {

        private static <V> Entry<V> value(V value) {
            return new Entry<>(value, null);
        }

        private static <V> Entry<V> value(V value, Duration timeout) {
            return new Entry<>(value, Instant.now().plus(timeout));
        }

        private static <V> Entry<V> value(V value, Instant expiresAt) {
            return new Entry<>(value, expiresAt);
        }

        private boolean isExpired() {
            return expiresAt != null && Instant.now().isAfter(expiresAt);
        }
    }
}
