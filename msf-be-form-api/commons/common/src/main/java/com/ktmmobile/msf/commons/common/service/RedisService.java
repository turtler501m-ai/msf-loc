package com.ktmmobile.msf.commons.common.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.common.utils.cache.CacheUtils;


@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring.data.redis", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RedisService<T> {

    private static final int REDIS_SCAN_COUNT = 100;

    private final RedisTemplate<String, T> redisTemplate;


    public void setValue(String key, T value) {
        redisTemplate.opsForValue().set(getRealKey(key), value);
    }

    public void setValue(String key, T value, Duration timeout) {
        redisTemplate.opsForValue().set(getRealKey(key), value, timeout);
    }

    public void setValue(String key, T value, LocalDate expireDate) {
        redisTemplate.opsForValue().set(getRealKey(key), value);
        expireAt(key, expireDate);
    }

    public boolean setValueIfAbsent(String key, T value) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(getRealKey(key), value);
        validateIfResultIsNotNull(result, "RedisTemplate.setIfAbsent()");
        return result;
    }

    public boolean setValueIfAbsent(String key, T value, Duration timeout) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(getRealKey(key), value, timeout);
        validateIfResultIsNotNull(result, "RedisTemplate.setIfAbsent()");
        return result;
    }

    private static void validateIfResultIsNotNull(Object result, String operation) {
        if (result == null) {
            throw new IllegalStateException(operation + " returned null.");
        }
    }

    /** Hash Type 사용 */
    public void setValues(String key, Map<String, T> values) {
        redisTemplate.opsForHash().putAll(getRealKey(key), values);
    }

    /** Hash Type 사용 */
    public void setValues(String key, Map<String, T> values, Duration timeout) {
        setValues(key, values);
        expire(key, timeout);
    }


    public T getValue(String key) {
        return redisTemplate.opsForValue().get(getRealKey(key));
    }

    public T getValue(String key, String hashKey) {
        HashOperations<String, String, T> operations = redisTemplate.opsForHash();
        return operations.get(getRealKey(key), hashKey);
    }


    public List<T> getValues(String pattern) {
        return getValues(pattern, 0);
    }

    public List<T> getValues(String pattern, int limit) {
        Set<String> keys = getRealKeys(pattern, limit);
        return redisTemplate.opsForValue().multiGet(keys);
    }


    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(getRealKey(key)));
    }

    /** Hash Type 사용 */
    public boolean hasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(getRealKey(key), hashKey);
    }


    public void delete(String key) {
        redisTemplate.delete(getRealKey(key));
    }

    public void delete(String key, String hashKey) {
        redisTemplate.opsForHash().delete(getRealKey(key), hashKey);
    }

    public void deleteAll(List<String> keys) {
        redisTemplate.delete(keys.stream().map(RedisService::getRealKey).toList());
    }

    public long increment(String key) {
        Long result = redisTemplate.opsForValue().increment(getRealKey(key));
        validateIfResultIsNotNull(result, "RedisTemplate.increment()");
        return result;
    }

    public long increment(String key, Duration timeout) {
        long result = increment(key);
        if (result == 1L) {
            expire(key, timeout);
        }
        return result;
    }

    private void expire(String key, Duration timeout) {
        redisTemplate.expire(getRealKey(key), timeout);
    }

    public long increment(String key, long delta) {
        Long result = redisTemplate.opsForValue().increment(getRealKey(key), delta);
        validateIfResultIsNotNull(result, "RedisTemplate.increment()");
        return result;
    }

    public long increment(String key, long delta, Duration timeout) {
        long result = increment(key, delta);
        if (result == delta) {
            expire(key, timeout);
        }
        return result;
    }

    public long increment(String key, LocalDate expireDate) {
        long result = increment(key);
        if (result == 1L) {
            expireAt(key, expireDate);
        }
        return result;
    }

    private void expireAt(String key, LocalDate expireDate) {
        redisTemplate.expireAt(getRealKey(key), expireDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public long increment(String key, long delta, LocalDate expireDate) {
        long result = increment(key, delta);
        if (result == delta) {
            expireAt(key, expireDate);
        }
        return result;
    }

    public long decrement(String key) {
        Long result = redisTemplate.opsForValue().decrement(getRealKey(key));
        validateIfResultIsNotNull(result, "RedisTemplate.decrement()");
        return result;
    }

    public long decrement(String key, long delta) {
        Long result = redisTemplate.opsForValue().decrement(getRealKey(key), delta);
        validateIfResultIsNotNull(result, "RedisTemplate.decrement()");
        return result;
    }


    /**
     * 일관된 Cache Key Prefix를 사용하기 위해
     * Redis에 접근(조회/쓰기)하는 모든 Operation에서 필수 사용
     */
    public static String getRealKey(String key) {
        return CacheUtils.getCachePrefix() + key;
    }

    public Set<String> getRealKeys(String pattern) {
        return getRealKeys(pattern, 0);
    }

    public Set<String> getRealKeys(String pattern, int limit) {
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
}
