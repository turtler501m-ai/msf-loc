package com.ktmmobile.msf.commons.cachecore.support.config;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.ktmmobile.msf.commons.cachecore.support.properties.CacheProperties;
import com.ktmmobile.msf.commons.common.cache.RedisConfig;

/**
 * cache-core 전용 Redis 설정
 */
@ConditionalOnProperty(prefix = "spring.data.redis", name = "enabled", havingValue = "true", matchIfMissing = true)
@Configuration(proxyBeanMethods = false)
public class CacheCoreRedisConfig {

    public static final String CACHE_LOAD_REDIS_CONNECTION_FACTORY = "cacheLoadRedisConnectionFactory";
    public static final String CACHE_LOAD_REDIS_TEMPLATE = "cacheLoadRedisTemplate";
    public static final String CACHE_LOAD_LOCK_PROVIDER = "cacheLoadLockProvider";

    /**
     * 캐시 적재 전용 Redis 연결 팩토리 생성
     *
     * @param redisConfig Redis 설정
     * @param cacheProperties 캐시 설정
     * @return Redis 연결 팩토리
     */
    @Bean(CACHE_LOAD_REDIS_CONNECTION_FACTORY)
    public RedisConnectionFactory cacheLoadRedisConnectionFactory(
        RedisConfig redisConfig,
        CacheProperties cacheProperties
    ) {
        return redisConfig.createConnectionFactory(cacheProperties.loadTimeout());
    }

    /**
     * 캐시 적재 분산 락 제공자 생성
     *
     * @param redisConnectionFactory Redis 연결 팩토리
     * @return 분산 락 제공자
     */
    @Bean(CACHE_LOAD_LOCK_PROVIDER)
    public LockProvider cacheLoadLockProvider(
        @Qualifier(CACHE_LOAD_REDIS_CONNECTION_FACTORY)
        RedisConnectionFactory redisConnectionFactory
    ) {
        return new RedisLockProvider(redisConnectionFactory);
    }

    /**
     * 캐시 적재 전용 RedisTemplate 생성
     *
     * @param redisConfig Redis 설정
     * @param redisConnectionFactory Redis 연결 팩토리
     * @param redisValueSerializer Redis 값 직렬화기
     * @return RedisTemplate
     */
    @Bean(CACHE_LOAD_REDIS_TEMPLATE)
    public RedisTemplate<String, Object> cacheLoadRedisTemplate(
        RedisConfig redisConfig,
        @Qualifier(CACHE_LOAD_REDIS_CONNECTION_FACTORY) RedisConnectionFactory redisConnectionFactory,
        @Qualifier(RedisConfig.REDIS_VALUE_SERIALIZER) RedisSerializer<Object> redisValueSerializer
    ) {
        return redisConfig.createRedisTemplate(redisConnectionFactory, redisValueSerializer);
    }
}
