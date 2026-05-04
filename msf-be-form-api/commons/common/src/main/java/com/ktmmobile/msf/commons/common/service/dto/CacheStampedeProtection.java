package com.ktmmobile.msf.commons.common.service.dto;

import java.time.Duration;

public record CacheStampedeProtection(
    String key,
    String staleKey,
    String lockKey,
    Duration timeToLive,
    Duration staleTimeToLive,
    Duration lockTimeToLive,
    Duration lockWaitTime,
    Duration lockRetryInterval
) {

    public Duration staleCacheTimeToLive() {
        return timeToLive.plus(staleTimeToLive);
    }
}
