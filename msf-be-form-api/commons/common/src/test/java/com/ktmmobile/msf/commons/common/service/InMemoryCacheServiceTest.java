package com.ktmmobile.msf.commons.common.service;

import java.time.Duration;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryCacheServiceTest {

    private final InMemoryCacheService<String> cacheService = new InMemoryCacheService<>();

    @Test
    void getTimeToLiveReturnsRemainingDurationForValueCache() {
        cacheService.setValue("key", "value", Duration.ofMinutes(1));

        Duration timeToLive = cacheService.getTimeToLive("key");

        assertThat(timeToLive)
            .isNotNull()
            .isPositive()
            .isLessThanOrEqualTo(Duration.ofMinutes(1));
    }

    @Test
    void getTimeToLiveReturnsRemainingDurationForHashCache() {
        cacheService.setValues("key", Map.of("hashKey", "value"), Duration.ofMinutes(1));

        Duration timeToLive = cacheService.getTimeToLive("key");

        assertThat(timeToLive)
            .isNotNull()
            .isPositive()
            .isLessThanOrEqualTo(Duration.ofMinutes(1));
    }

    @Test
    void getTimeToLiveReturnsNullWhenKeyHasNoExpiration() {
        cacheService.setValue("key", "value");

        assertThat(cacheService.getTimeToLive("key")).isNull();
    }

    @Test
    void getTimeToLiveReturnsNullWhenKeyDoesNotExist() {
        assertThat(cacheService.getTimeToLive("missing")).isNull();
    }
}
