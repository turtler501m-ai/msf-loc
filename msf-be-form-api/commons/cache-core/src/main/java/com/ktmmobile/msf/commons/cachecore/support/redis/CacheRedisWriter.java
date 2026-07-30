package com.ktmmobile.msf.commons.cachecore.support.redis;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.cachecore.support.config.CacheCoreRedisConfig;
import com.ktmmobile.msf.commons.cachecore.support.store.CacheStoreWriter;
import com.ktmmobile.msf.commons.cachecore.support.util.CacheStoreKeyGenerator;
import com.ktmmobile.msf.commons.common.utils.cache.CacheUtils;

/**
 * Redis 기반 캐시 저장소 쓰기 구현체
 */
@ConditionalOnProperty(prefix = "spring.data.redis", name = "enabled", havingValue = "true", matchIfMissing = true)
@Component
public class CacheRedisWriter implements CacheStoreWriter {

    private static final int REDIS_HASH_WRITE_BATCH_SIZE = 100;

    private final RedisTemplate<String, Object> redisTemplate;
    private final CacheStoreKeyGenerator cacheStoreKeyGenerator;

    /** Redis 캐시 쓰기 구현체 생성 */
    public CacheRedisWriter(
        @Qualifier(CacheCoreRedisConfig.CACHE_LOAD_REDIS_TEMPLATE) RedisTemplate<String, Object> redisTemplate,
        CacheStoreKeyGenerator cacheStoreKeyGenerator
    ) {
        this.redisTemplate = redisTemplate;
        this.cacheStoreKeyGenerator = cacheStoreKeyGenerator;
    }

    /** Redis Value 캐시 저장 */
    @Override
    public void setValue(String key, Object value, Duration timeout) {
        String realKey = getRealKey(key);
        if (timeout == null) {
            redisTemplate.opsForValue().set(realKey, value);
            return;
        }
        redisTemplate.opsForValue().set(realKey, value, timeout);
    }

    /** Redis Hash 캐시 항목 저장 */
    @Override
    public void setHashValue(String key, String hashKey, Object value, Duration timeout) {
        String realKey = getRealKey(key);
        redisTemplate.opsForHash().put(realKey, hashKey, value);
        if (timeout != null) {
            redisTemplate.expire(realKey, timeout);
        }
    }

    /** Redis Hash 캐시 전체 항목 교체 */
    @Override
    public void replaceHashValues(String key, Map<String, Object> values, Duration timeout) {
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
                redisTemplate.expire(temporaryKey, timeout);
            }
            redisTemplate.rename(temporaryKey, realKey);
        } catch (RuntimeException ex) {
            redisTemplate.delete(temporaryKey);
            throw ex;
        }
    }

    private void putAllInBatches(String realKey, Map<String, Object> values) {
        Map<String, Object> batch = LinkedHashMap.newLinkedHashMap(REDIS_HASH_WRITE_BATCH_SIZE);
        for (Map.Entry<String, Object> entry: values.entrySet()) {
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

    private String getRealKey(String key) {
        return CacheUtils.getCachePrefix() + cacheStoreKeyGenerator.generate(key);
    }
}
