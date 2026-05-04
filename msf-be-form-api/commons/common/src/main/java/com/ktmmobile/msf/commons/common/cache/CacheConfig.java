package com.ktmmobile.msf.commons.common.cache;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.ktmmobile.msf.commons.common.utils.cache.CacheUtils;

@Slf4j
@RequiredArgsConstructor
@EnableCaching
@Configuration(proxyBeanMethods = false)
public class CacheConfig implements CachingConfigurer {

    @Bean
    @ConditionalOnProperty(prefix = "spring.data.redis", name = "enabled", havingValue = "false")
    public CacheManager noOpCacheManager() {
        log.info(">>> CacheManager 미구성");
        return new NoOpCacheManager();
    }

    @Bean
    @DependsOn({"cacheUtilsInitializer", "environmentUtilsInitializer"})
    @ConditionalOnProperty(prefix = "spring.data.redis", name = "enabled", havingValue = "true", matchIfMissing = true)
    public CacheManager redisCacheManager(
        RedisConnectionFactory redisConnectionFactory,
        @Qualifier(RedisConfig.REDIS_VALUE_SERIALIZER) RedisSerializer<Object> redisValueSerializer
    ) {
        String prefixCacheName = CacheUtils.getCachePrefix();
        log.info(">>> CacheManager 구성: Prefix={}", prefixCacheName);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
            .defaultCacheConfig()
            .prefixCacheNameWith(prefixCacheName)
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisValueSerializer));

        Map<String, RedisCacheConfiguration> redisCacheConfigMap = new HashMap<>();

        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(redisConnectionFactory)
            .cacheDefaults(redisCacheConfiguration)
            .withInitialCacheConfigurations(redisCacheConfigMap)
            .build();
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new CustomCacheErrorHandler();
    }


    @Slf4j
    public static class CustomCacheErrorHandler implements CacheErrorHandler {

        @Override
        public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
            log.error("Unable to get from cache. key: {}, name: {}", key, cache.getName(), e);
        }

        @Override
        public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
            log.error("Unable to put into cache. key: {}, name: {}", key, cache.getName(), e);
        }

        @Override
        public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
            log.error("Unable to evict from cache. key: {}, name: {}", key, cache.getName(), e);
        }

        @Override
        public void handleCacheClearError(RuntimeException e, Cache cache) {
            log.error("Unable to clean cache. name: {}", cache.getName(), e);
        }
    }
}
