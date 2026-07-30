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

/**
 * 애플리케이션 메모리를 캐시 저장소로 사용하는 CacheService 구현체
 *
 * @param <T> 캐시 값 타입
 */
@ConditionalOnProperty(prefix = "spring.data.redis", name = "enabled", havingValue = "false")
@Service
public class InMemoryCacheService<T> implements CacheService<T> {

    private final Map<String, Entry<T>> valueStore = new ConcurrentHashMap<>();
    private final Map<String, Entry<Map<String, T>>> hashStore = new ConcurrentHashMap<>();

    /** Value 캐시에 값을 저장 */
    @Override
    public void setValue(String key, T value) {
        valueStore.put(key, Entry.value(value));
    }

    /** Value 캐시에 만료 시간과 함께 값을 저장 */
    @Override
    public void setValue(String key, T value, Duration timeout) {
        valueStore.put(key, Entry.value(value, timeout));
    }

    /** Value 캐시에 만료 일자와 함께 값을 저장 */
    @Override
    public void setValue(String key, T value, LocalDate expireDate) {
        valueStore.put(key, Entry.value(value, toExpireAt(expireDate)));
    }

    /** Key가 없을 때만 Value 캐시에 값을 저장 */
    @Override
    public boolean setValueIfAbsent(String key, T value) {
        purgeIfExpired(key);
        return valueStore.putIfAbsent(key, Entry.value(value)) == null;
    }

    /** Key가 없을 때만 Value 캐시에 만료 시간과 함께 값을 저장 */
    @Override
    public boolean setValueIfAbsent(String key, T value, Duration timeout) {
        purgeIfExpired(key);
        return valueStore.putIfAbsent(key, Entry.value(value, timeout)) == null;
    }

    /** Hash 캐시에 전체 값을 저장 */
    @Override
    public void setValues(String key, Map<String, T> values) {
        hashStore.put(key, Entry.value(new ConcurrentHashMap<>(values)));
    }

    /** Hash 캐시에 전체 값을 만료 시간과 함께 저장 */
    @Override
    public void setValues(String key, Map<String, T> values, Duration timeout) {
        hashStore.put(key, Entry.value(new ConcurrentHashMap<>(values), timeout));
    }

    /** Hash 캐시에 단일 값을 저장 */
    @Override
    public void setValue(String key, String hashKey, T value) {
        getOrCreateHash(key, null).put(hashKey, value);
    }

    /** Hash 캐시에 단일 값을 만료 시간과 함께 저장 */
    @Override
    public void setValue(String key, String hashKey, T value, Duration timeout) {
        getOrCreateHash(key, timeout).put(hashKey, value);
    }

    /** Hash 캐시의 전체 값을 교체 */
    @Override
    public void replaceValues(String key, Map<String, T> values) {
        valueStore.remove(key);
        hashStore.put(key, Entry.value(new ConcurrentHashMap<>(values)));
    }

    /** Hash 캐시의 전체 값을 만료 시간과 함께 교체 */
    @Override
    public void replaceValues(String key, Map<String, T> values, Duration timeout) {
        valueStore.remove(key);
        hashStore.put(key, Entry.value(new ConcurrentHashMap<>(values), timeout));
    }

    /** Value 캐시 값을 조회 */
    @Override
    public T getValue(String key) {
        Entry<T> entry = valueStore.get(key);
        if (entry == null || entry.isExpired()) {
            valueStore.remove(key);
            return null;
        }
        return entry.value();
    }

    /** Hash 캐시의 단일 값을 조회 */
    @Override
    public T getValue(String key, String hashKey) {
        Map<String, T> values = getHash(key);
        return values == null ? null : values.get(hashKey);
    }

    /** Hash 캐시에서 요청한 Hash Key 목록에 해당하는 값을 조회 */
    @Override
    public Map<String, T> getHashValues(String key, Collection<String> hashKeys) {
        Map<String, T> values = getHash(key);
        if (values == null || hashKeys.isEmpty()) {
            return Map.of();
        }

        Map<String, T> valuesByHashKey = new LinkedHashMap<>();
        for (String hashKey: hashKeys) {
            if (values.containsKey(hashKey)) {
                valuesByHashKey.put(hashKey, values.get(hashKey));
            }
        }
        return valuesByHashKey;
    }

    /** Hash 캐시의 전체 엔트리를 조회 */
    @Override
    public Map<String, T> getEntries(String key) {
        Map<String, T> values = getHash(key);
        return values == null ? Map.of() : Map.copyOf(values);
    }

    /** 패턴과 일치하는 Value 캐시 값을 조회 */
    @Override
    public List<T> getValues(String pattern) {
        return getValues(pattern, 0);
    }

    /** 패턴과 일치하는 Value 캐시 값을 지정한 개수만큼 조회 */
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

    /** Value 또는 Hash 캐시 Key의 남은 만료 시간을 조회 */
    @Override
    public Duration getTimeToLive(String key) {
        purgeIfExpired(key);
        Entry<T> valueEntry = valueStore.get(key);
        if (valueEntry != null) {
            return valueEntry.timeToLive();
        }

        Entry<Map<String, T>> hashEntry = hashStore.get(key);
        return hashEntry == null ? null : hashEntry.timeToLive();
    }

    /** Value 또는 Hash 캐시 Key 존재 여부를 조회 */
    @Override
    public boolean hasKey(String key) {
        return getValueByRealKey(key) != null || getHashByRealKey(key) != null;
    }

    /** Hash 캐시의 Hash Key 존재 여부를 조회 */
    @Override
    public boolean hasKey(String key, String hashKey) {
        Map<String, T> values = getHash(key);
        return values != null && values.containsKey(hashKey);
    }

    /** Value 또는 Hash 캐시를 삭제 */
    @Override
    public void delete(String key) {
        valueStore.remove(key);
        hashStore.remove(key);
    }

    /** Value 캐시 값이 일치할 때만 삭제 */
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

    /** Hash 캐시의 단일 값을 삭제 */
    @Override
    public void delete(String key, String hashKey) {
        Map<String, T> values = getHash(key);
        if (values != null) {
            values.remove(hashKey);
        }
    }

    /** 여러 캐시 Key를 삭제 */
    @Override
    public void deleteAll(List<String> keys) {
        keys.forEach(this::delete);
    }

    /** Value 캐시 숫자 값을 1 증가 */
    @Override
    public long increment(String key) {
        return increment(key, 1L);
    }

    /** Value 캐시 숫자 값을 1 증가하고 만료 시간을 설정 */
    @Override
    public long increment(String key, Duration timeout) {
        return incrementWithTimeout(key, 1L, timeout);
    }

    /** Value 캐시 숫자 값을 지정한 값만큼 증가 */
    @Override
    public long increment(String key, long delta) {
        return updateNumberValue(key, delta);
    }

    /** Value 캐시 숫자 값을 지정한 값만큼 증가하고 만료 시간을 설정 */
    @Override
    public long increment(String key, long delta, Duration timeout) {
        return incrementWithTimeout(key, delta, timeout);
    }

    /** Value 캐시 숫자 값을 1 증가하고 만료 일자를 설정 */
    @Override
    public long increment(String key, LocalDate expireDate) {
        return incrementWithExpireDate(key, 1L, expireDate);
    }

    /** Value 캐시 숫자 값을 지정한 값만큼 증가하고 만료 일자를 설정 */
    @Override
    public long increment(String key, long delta, LocalDate expireDate) {
        return incrementWithExpireDate(key, delta, expireDate);
    }

    /** Value 캐시 숫자 값을 1 감소 */
    @Override
    public long decrement(String key) {
        return decrement(key, 1L);
    }

    /** Value 캐시 숫자 값을 지정한 값만큼 감소 */
    @Override
    public long decrement(String key, long delta) {
        return updateNumberValue(key, -delta);
    }

    /** 패턴과 일치하는 캐시 Key 목록을 지정한 개수만큼 조회 */
    private Set<String> getRealKeys(String pattern, int limit) {
        purgeExpiredEntries();
        Pattern regex = Pattern.compile(toRegex(pattern));
        Map<String, Boolean> matchedKeys = new LinkedHashMap<>();
        addMatchedKeys(matchedKeys, valueStore.keySet(), regex, limit);
        addMatchedKeys(matchedKeys, hashStore.keySet(), regex, limit);
        return matchedKeys.keySet();
    }

    /** 숫자 값을 증가하고 최초 생성 시 만료 시간을 설정 */
    private long incrementWithTimeout(String key, long delta, Duration timeout) {
        long result = updateNumberValue(key, delta);
        if (result == delta) {
            expireValue(key, Instant.now().plus(timeout));
        }
        return result;
    }

    /** 숫자 값을 증가하고 최초 생성 시 만료 일자를 설정 */
    private long incrementWithExpireDate(String key, long delta, LocalDate expireDate) {
        long result = updateNumberValue(key, delta);
        if (result == delta) {
            expireValue(key, toExpireAt(expireDate));
        }
        return result;
    }

    /** 숫자 값을 갱신하고 결과 값을 반환 */
    private long updateNumberValue(String key, long delta) {
        purgeIfExpired(key);
        Entry<T> current = valueStore.get(key);
        long currentValue = current == null ? 0L : toLong(current.value());
        long updatedValue = currentValue + delta;
        Instant expiresAt = current == null ? null : current.expiresAt();
        valueStore.put(key, Entry.value(castNumber(updatedValue), expiresAt));
        return updatedValue;
    }

    /** Value 또는 Hash 캐시 엔트리에 만료 시각을 설정 */
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

    /** 만료된 전체 캐시 엔트리를 제거 */
    private void purgeExpiredEntries() {
        purgeExpiredEntries(valueStore);
        purgeExpiredEntries(hashStore);
    }

    /** 지정한 저장소에서 만료된 엔트리를 제거 */
    private <V> void purgeExpiredEntries(Map<String, Entry<V>> store) {
        store.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }

    /** 지정한 Key의 만료된 엔트리를 제거 */
    private void purgeIfExpired(String realKey) {
        removeIfExpired(valueStore, realKey);
        removeIfExpired(hashStore, realKey);
    }

    /** 지정한 저장소에서 만료된 단일 엔트리를 제거 */
    private <V> void removeIfExpired(Map<String, Entry<V>> store, String realKey) {
        Entry<V> entry = store.get(realKey);
        if (entry != null && entry.isExpired()) {
            store.remove(realKey);
        }
    }

    /** 저장소 Key로 Value 캐시 값을 조회 */
    private T getValueByRealKey(String realKey) {
        Entry<T> entry = valueStore.get(realKey);
        if (entry == null || entry.isExpired()) {
            valueStore.remove(realKey);
            return null;
        }
        return entry.value();
    }

    /** Hash 캐시 값을 조회 */
    private Map<String, T> getHash(String key) {
        return getHashByRealKey(key);
    }

    /** Hash 캐시 저장소를 조회하거나 새로 생성 */
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

    /** 저장소 Key로 Hash 캐시 값을 조회 */
    private Map<String, T> getHashByRealKey(String realKey) {
        Entry<Map<String, T>> entry = hashStore.get(realKey);
        if (entry == null || entry.isExpired()) {
            hashStore.remove(realKey);
            return null;
        }
        return entry.value();
    }

    /** 정규식과 일치하는 Key를 결과 목록에 추가 */
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

    /** 와일드카드 패턴을 정규식으로 변환 */
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

    /** 만료 일자를 시스템 기본 시간대의 Instant로 변환 */
    private Instant toExpireAt(LocalDate expireDate) {
        return expireDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

    /** long 값을 캐시 값 타입으로 변환 */
    @SuppressWarnings("unchecked")
    private T castNumber(long value) {
        Long boxedValue = value;
        return (T) boxedValue;
    }

    /** 캐시 값을 long으로 변환 */
    private long toLong(Object value) {
        if (value instanceof Number number) {
            return number.longValue();
        }
        throw new IllegalStateException("Stored value is not numeric: " + value);
    }

    /** 캐시 값과 만료 시각을 함께 보관하는 내부 엔트리 */
    private record Entry<V>(V value, Instant expiresAt) {

        /** 만료 시각이 없는 엔트리를 생성 */
        private static <V> Entry<V> value(V value) {
            return new Entry<>(value, null);
        }

        /** 만료 시간 기준 엔트리를 생성 */
        private static <V> Entry<V> value(V value, Duration timeout) {
            return new Entry<>(value, Instant.now().plus(timeout));
        }

        /** 만료 시각 기준 엔트리를 생성 */
        private static <V> Entry<V> value(V value, Instant expiresAt) {
            return new Entry<>(value, expiresAt);
        }

        /** 엔트리 만료 여부를 조회 */
        private boolean isExpired() {
            return expiresAt != null && Instant.now().isAfter(expiresAt);
        }

        /** 남은 만료 시간을 조회 */
        private Duration timeToLive() {
            if (expiresAt == null) {
                return null;
            }

            Duration remaining = Duration.between(Instant.now(), expiresAt);
            return remaining.isNegative() ? Duration.ZERO : remaining;
        }
    }
}
