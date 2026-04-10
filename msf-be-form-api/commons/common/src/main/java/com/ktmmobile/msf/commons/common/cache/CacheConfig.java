package com.ktmmobile.msf.commons.common.cache;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.DefaultTyping;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

import com.ktmmobile.msf.commons.common.utils.env.EnvironmentUtils;
import com.ktmmobile.msf.commons.common.utils.env.SpringCustomProperties;

@Slf4j
@RequiredArgsConstructor
@EnableCaching
@Configuration
public class CacheConfig implements CachingConfigurer {

    private final SpringCustomProperties springCustomProperties;

    @Bean
    @ConditionalOnProperty(prefix = "spring.data.redis", name = "enabled", havingValue = "false")
    public CacheManager noOpCacheManager() {
        log.info(">>> CacheManager 미구성");
        return new NoOpCacheManager();
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.data.redis", name = "enabled", havingValue = "true", matchIfMissing = true)
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        String prefixCacheName = getPrefixCacheName();
        log.info(">>> CacheManager 구성: Prefix={}", prefixCacheName);

        ObjectMapper mapper = createObjectMapperForSerializer();
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
            .defaultCacheConfig()
            .prefixCacheNameWith(prefixCacheName)
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJacksonJsonRedisSerializer(mapper)));

        Map<String, RedisCacheConfiguration> redisCacheConfigMap = new HashMap<>();

        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(redisConnectionFactory)
            .cacheDefaults(redisCacheConfiguration)
            .withInitialCacheConfigurations(redisCacheConfigMap)
            .build();
    }

    private String getPrefixCacheName() {
        String prefixCacheName = springCustomProperties.applicationNameAbbreviated() + "::";
        if (EnvironmentUtils.isLocalProfile()) {
            return EnvironmentUtils.getLocalProfile() + ":" + prefixCacheName;
        }
        return prefixCacheName;
    }

    private ObjectMapper createObjectMapperForSerializer() {
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
            .allowIfSubType(Object.class)
            .build();
        return JsonMapper.builder()
            .activateDefaultTyping(ptv, DefaultTyping.NON_FINAL_AND_ENUMS, JsonTypeInfo.As.PROPERTY)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
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
