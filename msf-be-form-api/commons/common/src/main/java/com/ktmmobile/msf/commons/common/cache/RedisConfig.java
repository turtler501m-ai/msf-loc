package com.ktmmobile.msf.commons.common.cache;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.lettuce.core.ReadFrom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.data.redis.autoconfigure.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.DefaultTyping;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = "spring.data.redis", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RedisConfig {

    // NOTE:
    // Boot 4 환경에서 DataRedisProperties 바인딩 이슈가 있어 임시로 @Value 기반으로 우회합니다.
    // 기존 방식은 아래 주석 참고.
    //
    // private final DataRedisProperties redisProperties;
    //
    // serverConfig.setHostName(redisProperties.getHost());
    // serverConfig.setPort(redisProperties.getPort());

    @Value("${spring.data.redis.host:localhost}")
    private String redisHost;

    @Value("${spring.data.redis.port:6379}")
    private int redisPort;

    @Value("${spring.data.redis.timeout:500ms}")
    private Duration timeout;

    @Bean
    @ConditionalOnProperty(value = "spring.data.redis.host", havingValue = "localhost")
    public RedisConnectionFactory redisConnectionFactory() {
        log.info(">>> Redis 구성: Standalone Mode");

        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration();
        serverConfig.setHostName(redisHost);
        serverConfig.setPort(redisPort);

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
            .commandTimeout(this.timeout)
            .build();

        return new LettuceConnectionFactory(serverConfig, clientConfig);
    }

    @Bean
    @ConditionalOnMissingBean(RedisConnectionFactory.class)
    public LettuceClientConfigurationBuilderCustomizer lettuceClientConfigurationBuilderCustomizer() {
        log.info(">>> Redis 구성: Cluster Mode");
        return clientConfigurationBuilder -> clientConfigurationBuilder.readFrom(ReadFrom.REPLICA_PREFERRED)
            .commandTimeout(this.timeout)
            .build();
    }

    /**
     * 레디스 RedisTemplate
     */
    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        ObjectMapper mapper = createObjectMapperForSerializer();
        GenericJacksonJsonRedisSerializer serializer = new GenericJacksonJsonRedisSerializer(mapper);

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);

        return redisTemplate;
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

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

}
