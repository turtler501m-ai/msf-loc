package com.ktmmobile.msf.commons.common.service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.common.service.port.CacheService;
import com.ktmmobile.msf.commons.common.utils.cache.CacheUtils;

/**
 * Redis를 캐시 저장소로 사용하는 CacheService 구현체
 *
 * @param <T> 캐시 값 타입
 */
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring.data.redis", name = "enabled", havingValue = "true", matchIfMissing = true)
@Service
public class RedisCacheService<T> implements CacheService<T> {

    private static final int REDIS_SCAN_COUNT = 100;
    private static final int REDIS_HASH_WRITE_BATCH_SIZE = 500;

    private static final RedisScript<Long> DELETE_IF_VALUE_EQUALS_SCRIPT = new DefaultRedisScript<>(
        """
            if redis.call('get', KEYS[1]) == ARGV[1] then
                return redis.call('del', KEYS[1])
            else
                return 0
            end
            """,
        Long.class
    );

    private final RedisTemplate<String, T> redisTemplate;

    /** Value 캐시에 값을 저장 */
    @Override
    public void setValue(String key, T value) {
        redisTemplate.opsForValue().set(getRealKey(key), value);
    }

    /** Value 캐시에 만료 시간과 함께 값을 저장 */
    @Override
    public void setValue(String key, T value, Duration timeout) {
        redisTemplate.opsForValue().set(getRealKey(key), value, timeout);
    }

    /** Value 캐시에 만료 일자와 함께 값을 저장 */
    @Override
    public void setValue(String key, T value, LocalDate expireDate) {
        String realKey = getRealKey(key);
        redisTemplate.opsForValue().set(realKey, value);
        expireAt(realKey, expireDate);
    }

    /** Key가 없을 때만 Value 캐시에 값을 저장 */
    @Override
    public boolean setValueIfAbsent(String key, T value) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(getRealKey(key), value);
        return requireNonNull(result, "RedisTemplate.setIfAbsent()");
    }

    /** Key가 없을 때만 Value 캐시에 만료 시간과 함께 값을 저장 */
    @Override
    public boolean setValueIfAbsent(String key, T value, Duration timeout) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(getRealKey(key), value, timeout);
        return requireNonNull(result, "RedisTemplate.setIfAbsent()");
    }

    /** Redis 작업 결과가 null이면 예외로 처리 */
    private static <V> V requireNonNull(V result, String operation) {
        if (result == null) {
            throw new IllegalStateException(operation + " returned null.");
        }
        return result;
    }

    /** Hash 캐시에 전체 값을 저장 */
    @Override
    public void setValues(String key, Map<String, T> values) {
        putAllInBatches(getRealKey(key), values);
    }

    /** Hash 캐시에 전체 값을 만료 시간과 함께 저장 */
    @Override
    public void setValues(String key, Map<String, T> values, Duration timeout) {
        String realKey = getRealKey(key);
        putAllInBatches(realKey, values);
        expire(realKey, timeout);
    }

    /** Hash 캐시에 단일 값을 저장 */
    @Override
    public void setValue(String key, String hashKey, T value) {
        redisTemplate.opsForHash().put(getRealKey(key), hashKey, value);
    }

    /** Hash 캐시에 단일 값을 만료 시간과 함께 저장 */
    @Override
    public void setValue(String key, String hashKey, T value, Duration timeout) {
        String realKey = getRealKey(key);
        redisTemplate.opsForHash().put(realKey, hashKey, value);
        expire(realKey, timeout);
    }

    /** Hash 캐시의 전체 값을 교체 */
    @Override
    public void replaceValues(String key, Map<String, T> values) {
        replaceValues(key, values, null);
    }

    /** Hash 캐시의 전체 값을 만료 시간과 함께 교체 */
    @Override
    public void replaceValues(String key, Map<String, T> values, Duration timeout) {
        String realKey = getRealKey(key);
        if (values.isEmpty()) {
            redisTemplate.delete(realKey);
            return;
        }

        String temporaryKey = realKey + ":tmp:replace:" + UUID.randomUUID();
        try {
            redisTemplate.delete(temporaryKey);
            putAllInBatches(temporaryKey, values);
            if (timeout != null) {
                expire(temporaryKey, timeout);
            }
            redisTemplate.rename(temporaryKey, realKey);
        } catch (RuntimeException ex) {
            redisTemplate.delete(temporaryKey);
            throw ex;
        }
    }

    /** Value 캐시 값을 조회 */
    @Override
    public T getValue(String key) {
        return redisTemplate.opsForValue().get(getRealKey(key));
    }

    /** Hash 캐시의 단일 값을 조회 */
    @Override
    public T getValue(String key, String hashKey) {
        HashOperations<String, String, T> operations = redisTemplate.opsForHash();
        return operations.get(getRealKey(key), hashKey);
    }

    /** Hash 캐시에서 요청한 Hash Key 목록에 해당하는 값을 조회 */
    @Override
    public Map<String, T> getHashValues(String key, Collection<String> hashKeys) {
        if (hashKeys.isEmpty()) {
            return Map.of();
        }

        List<Object> orderedHashKeys = hashKeys.stream()
            .distinct()
            .map(hashKey -> (Object) hashKey)
            .toList();
        HashOperations<String, Object, T> operations = redisTemplate.opsForHash();
        List<T> values = operations.multiGet(getRealKey(key), orderedHashKeys);

        Map<String, T> valuesByHashKey = new LinkedHashMap<>();
        for (int index = 0; index < orderedHashKeys.size(); index++) {
            T value = values.get(index);
            if (value != null) {
                valuesByHashKey.put((String) orderedHashKeys.get(index), value);
            }
        }
        return valuesByHashKey;
    }

    /** Hash 캐시의 전체 엔트리를 조회 */
    @Override
    public Map<String, T> getEntries(String key) {
        HashOperations<String, String, T> operations = redisTemplate.opsForHash();
        return operations.entries(getRealKey(key));
    }

    /** 패턴과 일치하는 Value 캐시 값을 조회 */
    @Override
    public List<T> getValues(String pattern) {
        return getValues(pattern, 0);
    }

    /** 패턴과 일치하는 Value 캐시 값을 지정한 개수만큼 조회 */
    @Override
    public List<T> getValues(String pattern, int limit) {
        Set<String> keys = getRealKeys(pattern, limit);
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /** Value 또는 Hash 캐시 Key의 남은 만료 시간을 조회 */
    @Override
    public Duration getTimeToLive(String key) {
        Long ttlMillis = redisTemplate.getExpire(getRealKey(key), TimeUnit.MILLISECONDS);
        long ttl = requireNonNull(ttlMillis, "RedisTemplate.getExpire()");
        return ttl < 0L ? null : Duration.ofMillis(ttl);
    }

    /** Value 또는 Hash 캐시 Key 존재 여부를 조회 */
    @Override
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(getRealKey(key)));
    }

    /** Hash 캐시의 Hash Key 존재 여부를 조회 */
    @Override
    public boolean hasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(getRealKey(key), hashKey);
    }

    /** Value 또는 Hash 캐시를 삭제 */
    @Override
    public void delete(String key) {
        redisTemplate.delete(getRealKey(key));
    }

    /** Value 캐시 값이 일치할 때만 삭제 */
    @Override
    public boolean deleteIfValueEquals(String key, T value) {
        Long result = redisTemplate.execute(DELETE_IF_VALUE_EQUALS_SCRIPT, List.of(getRealKey(key)), value);
        return Long.valueOf(1L).equals(result);
    }

    /** Hash 캐시의 단일 값을 삭제 */
    @Override
    public void delete(String key, String hashKey) {
        redisTemplate.opsForHash().delete(getRealKey(key), hashKey);
    }

    /** 여러 캐시 Key를 삭제 */
    @Override
    public void deleteAll(List<String> keys) {
        redisTemplate.delete(keys.stream().map(this::getRealKey).toList());
    }

    /** Value 캐시 숫자 값을 1 증가 */
    @Override
    public long increment(String key) {
        Long result = redisTemplate.opsForValue().increment(getRealKey(key));
        return requireNonNull(result, "RedisTemplate.increment()");
    }

    /** Value 캐시 숫자 값을 1 증가하고 만료 시간을 설정 */
    @Override
    public long increment(String key, Duration timeout) {
        String realKey = getRealKey(key);
        long result = incrementByRealKey(realKey, 1L);
        if (result == 1L) {
            expire(realKey, timeout);
        }
        return result;
    }

    /** Redis Key에 만료 시간을 설정 */
    private void expire(String realKey, Duration timeout) {
        redisTemplate.expire(realKey, timeout);
    }

    /** Value 캐시 숫자 값을 지정한 값만큼 증가 */
    @Override
    public long increment(String key, long delta) {
        return incrementByRealKey(getRealKey(key), delta);
    }

    /** Value 캐시 숫자 값을 지정한 값만큼 증가하고 만료 시간을 설정 */
    @Override
    public long increment(String key, long delta, Duration timeout) {
        String realKey = getRealKey(key);
        long result = incrementByRealKey(realKey, delta);
        if (result == delta) {
            expire(realKey, timeout);
        }
        return result;
    }

    /** Value 캐시 숫자 값을 1 증가하고 만료 일자를 설정 */
    @Override
    public long increment(String key, LocalDate expireDate) {
        String realKey = getRealKey(key);
        long result = incrementByRealKey(realKey, 1L);
        if (result == 1L) {
            expireAt(realKey, expireDate);
        }
        return result;
    }

    /** Redis Key에 만료 일자를 설정 */
    private void expireAt(String realKey, LocalDate expireDate) {
        Instant expireAt = expireDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        redisTemplate.expireAt(realKey, expireAt);
    }

    /** Value 캐시 숫자 값을 지정한 값만큼 증가하고 만료 일자를 설정 */
    @Override
    public long increment(String key, long delta, LocalDate expireDate) {
        String realKey = getRealKey(key);
        long result = incrementByRealKey(realKey, delta);
        if (result == delta) {
            expireAt(realKey, expireDate);
        }
        return result;
    }

    /** Value 캐시 숫자 값을 1 감소 */
    @Override
    public long decrement(String key) {
        Long result = redisTemplate.opsForValue().decrement(getRealKey(key));
        return requireNonNull(result, "RedisTemplate.decrement()");
    }

    /** Value 캐시 숫자 값을 지정한 값만큼 감소 */
    @Override
    public long decrement(String key, long delta) {
        Long result = redisTemplate.opsForValue().decrement(getRealKey(key), delta);
        return requireNonNull(result, "RedisTemplate.decrement()");
    }

    /** 패턴과 일치하는 실제 Redis Key 목록을 조회 */
    private Set<String> getRealKeys(String pattern, int limit) {
        Set<String> keys = new HashSet<>();
        ScanOptions options = ScanOptions.scanOptions()
            .match(getRealKey(pattern))
            .count(REDIS_SCAN_COUNT)
            .build();
        try (Cursor<String> cursor = redisTemplate.scan(options)) {
            while (cursor.hasNext() && (limit == 0 || keys.size() < limit)) {
                keys.add(cursor.next());
            }
        }
        return keys;
    }

    /** Hash 값을 Redis 기본 배치 크기로 나누어 저장 */
    private void putAllInBatches(String realKey, Map<String, T> values) {
        putAllInBatches(redisTemplate, realKey, values);
    }

    /** Hash 값을 지정한 RedisTemplate으로 배치 저장 */
    private void putAllInBatches(RedisTemplate<String, T> redisTemplate, String realKey, Map<String, T> values) {
        if (values.isEmpty()) {
            return;
        }

        Map<String, T> batch = LinkedHashMap.newLinkedHashMap(REDIS_HASH_WRITE_BATCH_SIZE);
        for (Map.Entry<String, T> entry: values.entrySet()) {
            batch.put(entry.getKey(), entry.getValue());
            if (batch.size() >= REDIS_HASH_WRITE_BATCH_SIZE) {
                redisTemplate.opsForHash().putAll(realKey, batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            redisTemplate.opsForHash().putAll(realKey, batch);
        }
    }

    /** 실제 Redis Key의 숫자 값을 지정한 값만큼 증가 */
    private long incrementByRealKey(String realKey, long delta) {
        Long result = redisTemplate.opsForValue().increment(realKey, delta);
        return requireNonNull(result, "RedisTemplate.increment()");
    }

    /** 설정된 캐시 Prefix를 포함한 실제 Redis Key를 생성 */
    private String getRealKey(String key) {
        return CacheUtils.getCachePrefix() + key;
    }
}
