package com.ktmmobile.msf.commons.common.scheduling;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import com.ktmmobile.msf.commons.common.utils.cache.CacheUtils;
import com.ktmmobile.msf.commons.common.utils.env.SpringCustomProperties;

@EnableSchedulerLock(defaultLockAtMostFor = "10m", defaultLockAtLeastFor = "1m")
@ConditionalOnProperty(prefix = "spring.data.redis", name = "enabled", havingValue = "true", matchIfMissing = true)
@Configuration(proxyBeanMethods = false)
public class SchedulerLockConfig {

    public static final String SCHEDULER_LOCK_PROVIDER = "schedulerLockProvider";

    /**
     * 분산 락 Provider 생성
     */
    @Primary
    @Bean(SCHEDULER_LOCK_PROVIDER)
    public LockProvider schedulerLockProvider(
        RedisConnectionFactory redisConnectionFactory,
        SpringCustomProperties springCustomProperties
    ) {
        return new RedisLockProvider.Builder(redisConnectionFactory)
            .environment(springCustomProperties.applicationNameAbbreviated())
            .keyPrefix(CacheUtils.getCachePrefix() + "job-lock")
            .build();
    }
}
