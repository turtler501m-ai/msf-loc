package com.ktmmobile.msf.commons.cachecore.support.redis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.cachecore.support.config.CacheCoreRedisConfig;
import com.ktmmobile.msf.commons.cachecore.support.store.CacheStoreReader;
import com.ktmmobile.msf.commons.cachecore.support.util.CacheStoreKeyGenerator;
import com.ktmmobile.msf.commons.common.utils.cache.CacheUtils;

/**
 * Redis 기반 캐시 저장소 조회 구현체
 */
@ConditionalOnProperty(prefix = "spring.data.redis", name = "enabled", havingValue = "true", matchIfMissing = true)
@Component
public class CacheRedisReader implements CacheStoreReader {

    private final RedisTemplate<String, Object> redisTemplate;
    private final CacheStoreKeyGenerator cacheStoreKeyGenerator;

    /**
     * Redis 캐시 조회 구현체 생성
     */
    public CacheRedisReader(
        @Qualifier(CacheCoreRedisConfig.CACHE_LOAD_REDIS_TEMPLATE) RedisTemplate<String, Object> redisTemplate,
        CacheStoreKeyGenerator cacheStoreKeyGenerator
    ) {
        this.redisTemplate = redisTemplate;
        this.cacheStoreKeyGenerator = cacheStoreKeyGenerator;
    }

    /**
     * Redis Hash 캐시 전체 항목 조회
     */
    @Override
    public Map<String, Object> getHashEntries(String key) {
        HashOperations<String, String, Object> operations = redisTemplate.opsForHash();
        return operations.entries(getRealKey(key));
    }

    /**
     * Redis Hash 캐시 항목 수 조회
     */
    @Override
    public long getHashSize(String key) {
        Long size = redisTemplate.opsForHash().size(getRealKey(key));
        return size == null ? 0L : size;
    }

    /**
     * Redis Hash 캐시 키 존재 여부 확인
     */
    @Override
    public boolean hasHashKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(getRealKey(key), hashKey);
    }

    /**
     * Redis Value 캐시 키 존재 여부 확인
     */
    @Override
    public boolean hasValueKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(getRealKey(key)));
    }

    private String getRealKey(String key) {
        return CacheUtils.getCachePrefix() + cacheStoreKeyGenerator.generate(key);
    }
}
