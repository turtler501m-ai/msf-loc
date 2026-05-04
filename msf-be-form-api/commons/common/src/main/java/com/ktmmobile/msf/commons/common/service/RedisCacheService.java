package com.ktmmobile.msf.commons.common.service;

import java.time.Duration;
import java.time.Instant;
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
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.common.service.port.CacheService;
import com.ktmmobile.msf.commons.common.utils.cache.CacheUtils;

@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring.data.redis", name = "enabled", havingValue = "true", matchIfMissing = true)
@Service
public class RedisCacheService<T> implements CacheService<T> {

    private static final int REDIS_SCAN_COUNT = 100;
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

    @Override
    public void setValue(String key, T value) {
        redisTemplate.opsForValue().set(getRealKey(key), value);
    }

    @Override
    public void setValue(String key, T value, Duration timeout) {
        redisTemplate.opsForValue().set(getRealKey(key), value, timeout);
    }

    @Override
    public void setValue(String key, T value, LocalDate expireDate) {
        String realKey = getRealKey(key);
        redisTemplate.opsForValue().set(realKey, value);
        expireAt(realKey, expireDate);
    }

    @Override
    public boolean setValueIfAbsent(String key, T value) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(getRealKey(key), value);
        return requireNonNull(result, "RedisTemplate.setIfAbsent()");
    }

    @Override
    public boolean setValueIfAbsent(String key, T value, Duration timeout) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(getRealKey(key), value, timeout);
        return requireNonNull(result, "RedisTemplate.setIfAbsent()");
    }

    private static <V> V requireNonNull(V result, String operation) {
        if (result == null) {
            throw new IllegalStateException(operation + " returned null.");
        }
        return result;
    }

    @Override
    public void setValues(String key, Map<String, T> values) {
        redisTemplate.opsForHash().putAll(getRealKey(key), values);
    }

    @Override
    public void setValues(String key, Map<String, T> values, Duration timeout) {
        String realKey = getRealKey(key);
        redisTemplate.opsForHash().putAll(realKey, values);
        expire(realKey, timeout);
    }

    @Override
    public T getValue(String key) {
        return redisTemplate.opsForValue().get(getRealKey(key));
    }

    @Override
    public T getValue(String key, String hashKey) {
        HashOperations<String, String, T> operations = redisTemplate.opsForHash();
        return operations.get(getRealKey(key), hashKey);
    }

    @Override
    public List<T> getValues(String pattern) {
        return getValues(pattern, 0);
    }

    @Override
    public List<T> getValues(String pattern, int limit) {
        Set<String> keys = getRealKeys(pattern, limit);
        return redisTemplate.opsForValue().multiGet(keys);
    }

    @Override
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(getRealKey(key)));
    }

    @Override
    public boolean hasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(getRealKey(key), hashKey);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(getRealKey(key));
    }

    @Override
    public boolean deleteIfValueEquals(String key, T value) {
        Long result = redisTemplate.execute(DELETE_IF_VALUE_EQUALS_SCRIPT, List.of(getRealKey(key)), value);
        return Long.valueOf(1L).equals(result);
    }

    @Override
    public void delete(String key, String hashKey) {
        redisTemplate.opsForHash().delete(getRealKey(key), hashKey);
    }

    @Override
    public void deleteAll(List<String> keys) {
        redisTemplate.delete(keys.stream().map(this::getRealKey).toList());
    }

    @Override
    public long increment(String key) {
        Long result = redisTemplate.opsForValue().increment(getRealKey(key));
        return requireNonNull(result, "RedisTemplate.increment()");
    }

    @Override
    public long increment(String key, Duration timeout) {
        String realKey = getRealKey(key);
        long result = incrementByRealKey(realKey, 1L);
        if (result == 1L) {
            expire(realKey, timeout);
        }
        return result;
    }

    private void expire(String realKey, Duration timeout) {
        redisTemplate.expire(realKey, timeout);
    }

    @Override
    public long increment(String key, long delta) {
        return incrementByRealKey(getRealKey(key), delta);
    }

    @Override
    public long increment(String key, long delta, Duration timeout) {
        String realKey = getRealKey(key);
        long result = incrementByRealKey(realKey, delta);
        if (result == delta) {
            expire(realKey, timeout);
        }
        return result;
    }

    @Override
    public long increment(String key, LocalDate expireDate) {
        String realKey = getRealKey(key);
        long result = incrementByRealKey(realKey, 1L);
        if (result == 1L) {
            expireAt(realKey, expireDate);
        }
        return result;
    }

    private void expireAt(String realKey, LocalDate expireDate) {
        Instant expireAt = expireDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        redisTemplate.expireAt(realKey, expireAt);
    }

    @Override
    public long increment(String key, long delta, LocalDate expireDate) {
        String realKey = getRealKey(key);
        long result = incrementByRealKey(realKey, delta);
        if (result == delta) {
            expireAt(realKey, expireDate);
        }
        return result;
    }

    @Override
    public long decrement(String key) {
        Long result = redisTemplate.opsForValue().decrement(getRealKey(key));
        return requireNonNull(result, "RedisTemplate.decrement()");
    }

    @Override
    public long decrement(String key, long delta) {
        Long result = redisTemplate.opsForValue().decrement(getRealKey(key), delta);
        return requireNonNull(result, "RedisTemplate.decrement()");
    }

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

    private long incrementByRealKey(String realKey, long delta) {
        Long result = redisTemplate.opsForValue().increment(realKey, delta);
        return requireNonNull(result, "RedisTemplate.increment()");
    }

    /**
     * 일관된 Cache Key Prefix를 사용하기 위해
     * Redis에 접근(조회/쓰기)하는 모든 Operation에서 필수 사용
     */
    private String getRealKey(String key) {
        return CacheUtils.getCachePrefix() + key;
    }
}
