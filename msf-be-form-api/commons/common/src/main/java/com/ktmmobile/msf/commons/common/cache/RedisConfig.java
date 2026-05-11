package com.ktmmobile.msf.commons.common.cache;

import java.time.Duration;
import java.util.List;

import io.lettuce.core.ReadFrom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.data.redis.autoconfigure.DataRedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring.data.redis", name = "enabled", havingValue = "true", matchIfMissing = true)
@Configuration(proxyBeanMethods = false)
public class RedisConfig {

    public static final String REDIS_VALUE_SERIALIZER = "redisValueSerializer";

    private final DataRedisProperties redisProperties;

    @Primary
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return createConnectionFactory(redisProperties.getTimeout());
    }

    public RedisConnectionFactory createConnectionFactory(Duration commandTimeout) {
        if (hasMasterReplicaNodes()) {
            return masterReplicaConnectionFactory(commandTimeout);
        }
        return standaloneConnectionFactory(commandTimeout);
    }

    private boolean hasMasterReplicaNodes() {
        return redisProperties.getMasterreplica() != null
            && redisProperties.getMasterreplica().getNodes() != null
            && redisProperties.getMasterreplica().getNodes().stream().anyMatch(StringUtils::hasText);
    }

    private RedisConnectionFactory standaloneConnectionFactory(Duration commandTimeout) {
        log.info(">>> Redis 구성: Standalone Mode, commandTimeout={}", commandTimeout);

        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
        applyAuthentication(serverConfig);
        serverConfig.setDatabase(redisProperties.getDatabase());

        return new LettuceConnectionFactory(serverConfig,
            LettuceClientConfiguration.builder()
                .commandTimeout(commandTimeout)
                .build());
    }

    private RedisConnectionFactory masterReplicaConnectionFactory(Duration commandTimeout) {
        log.info(">>> Redis 구성: Master/Replica Mode, commandTimeout={}", commandTimeout);

        List<RedisNode> nodes = redisProperties.getMasterreplica().getNodes().stream()
            .filter(StringUtils::hasText)
            .map(RedisNode::fromString)
            .toList();
        RedisNode master = nodes.getFirst();
        RedisStaticMasterReplicaConfiguration serverConfig = new RedisStaticMasterReplicaConfiguration(master.getHost(), master.getPort());
        nodes.stream()
            .skip(1)
            .forEach(node -> serverConfig.addNode(node.getHost(), node.getPort()));
        applyAuthentication(serverConfig);
        serverConfig.setDatabase(redisProperties.getDatabase());

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
            .readFrom(ReadFrom.REPLICA_PREFERRED)
            .commandTimeout(commandTimeout)
            .build();
        return new LettuceConnectionFactory(serverConfig, clientConfig);
    }

    private void applyAuthentication(RedisStandaloneConfiguration serverConfig) {
        if (StringUtils.hasText(redisProperties.getUsername())) {
            serverConfig.setUsername(redisProperties.getUsername());
        }
        if (StringUtils.hasText(redisProperties.getPassword())) {
            serverConfig.setPassword(RedisPassword.of(redisProperties.getPassword()));
        }
    }

    private void applyAuthentication(RedisStaticMasterReplicaConfiguration serverConfig) {
        if (StringUtils.hasText(redisProperties.getUsername())) {
            serverConfig.setUsername(redisProperties.getUsername());
        }
        if (StringUtils.hasText(redisProperties.getPassword())) {
            serverConfig.setPassword(RedisPassword.of(redisProperties.getPassword()));
        }
    }

    /**
     * RedisTemplate
     */
    @Primary
    @Bean
    public RedisTemplate<String, Object> redisTemplate(
        RedisConnectionFactory redisConnectionFactory,
        @Qualifier(REDIS_VALUE_SERIALIZER) RedisSerializer<Object> redisValueSerializer
    ) {
        return createRedisTemplate(redisConnectionFactory, redisValueSerializer);
    }

    public RedisTemplate<String, Object> createRedisTemplate(
        RedisConnectionFactory redisConnectionFactory,
        RedisSerializer<Object> redisValueSerializer
    ) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(redisValueSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(redisValueSerializer);

        return redisTemplate;
    }

    @Bean(REDIS_VALUE_SERIALIZER)
    public RedisSerializer<Object> redisValueSerializer(ObjectMapper objectMapper) {
        return GenericJacksonJsonRedisSerializer.builder(objectMapper::rebuild)
            .enableDefaultTyping(redisPolymorphicTypeValidator())
            .enableSpringCacheNullValueSupport()
            .customize(builder -> builder.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false))
            .build();
    }

    private PolymorphicTypeValidator redisPolymorphicTypeValidator() {
        return BasicPolymorphicTypeValidator.builder()
            .allowIfSubType(Object.class)
            .build();
    }
}
