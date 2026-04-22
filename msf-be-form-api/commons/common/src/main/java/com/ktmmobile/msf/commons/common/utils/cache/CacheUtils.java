package com.ktmmobile.msf.commons.common.utils.cache;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.DefaultTyping;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

import com.ktmmobile.msf.commons.common.utils.env.EnvironmentUtils;
import com.ktmmobile.msf.commons.common.utils.env.SpringCustomProperties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CacheUtils {

    private static SpringCustomProperties springCustomProperties;
    private static ObjectMapper objectMapper;

    private static ObjectMapper objectMapperForSerializer;

    static void initialize(
        SpringCustomProperties springCustomProperties,
        ObjectMapper objectMapper
    ) {
        CacheUtils.springCustomProperties = springCustomProperties;
        CacheUtils.objectMapper = objectMapper;
        objectMapperForSerializer = getObjectMapperForSerializer();
    }

    private static ObjectMapper getObjectMapperForSerializer() {
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
            .allowIfSubType(Object.class)
            .build();
        objectMapperForSerializer = objectMapper.rebuild()
            .activateDefaultTyping(ptv, DefaultTyping.NON_FINAL_AND_ENUMS, JsonTypeInfo.As.PROPERTY)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .build();

        return objectMapperForSerializer;
    }

    public static String getCachePrefix() {
        String appName = springCustomProperties.applicationNameAbbreviated();
        if (EnvironmentUtils.isLocal()) {
            return appName + "::" + EnvironmentUtils.getLocalProfileCode() + ":";
        }
        return appName + "::";
    }

    public static RedisSerializer<String> createKeySerializer() {
        return new StringRedisSerializer();
    }

    public static RedisSerializer<Object> createValueSerializer() {
        return new GenericJacksonJsonRedisSerializer(objectMapperForSerializer);
    }
}
