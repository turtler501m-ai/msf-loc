package com.ktmmobile.msf.commons.cachecore.support.properties;

import java.time.Duration;
import java.util.Collections;
import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * cache-core 설정 프로퍼티
 */
@ConfigurationProperties(prefix = "cache-core")
public record CacheProperties(
    String keyPrefix,
    Duration loadTimeout,
    AutoLoad autoLoad,
    Stampede stampede,
    LoadLock loadLock,
    RedisWriteLock redisWriteLock
) {

    private static final String DEFAULT_KEY_PREFIX = "cache-core:";
    private static final Duration DEFAULT_LOAD_TIMEOUT = Duration.ofSeconds(10);

    /**
     * 캐시 키 접두어 반환
     *
     * @return 캐시 키 접두어
     */
    public String keyPrefix() {
        return keyPrefix == null || keyPrefix.isBlank() ? DEFAULT_KEY_PREFIX : keyPrefix;
    }

    /**
     * 캐시 적재 제한 시간 반환
     *
     * @return 캐시 적재 제한 시간
     */
    public Duration loadTimeout() {
        return loadTimeout == null ? DEFAULT_LOAD_TIMEOUT : loadTimeout;
    }

    /**
     * 자동 적재 설정 반환
     *
     * @return 자동 적재 설정
     */
    public AutoLoad autoLoad() {
        return autoLoad == null ? AutoLoad.defaults() : autoLoad;
    }

    /**
     * 스탬피드 방지 설정 반환
     *
     * @return 스탬피드 방지 설정
     */
    public Stampede stampede() {
        return stampede == null ? Stampede.defaults() : stampede;
    }

    /**
     * 캐시 적재 락 설정 반환
     *
     * @return 캐시 적재 락 설정
     */
    public LoadLock loadLock() {
        return loadLock == null ? LoadLock.defaults() : loadLock;
    }

    /**
     * Redis 쓰기 락 설정 반환
     *
     * @return Redis 쓰기 락 설정
     */
    public RedisWriteLock redisWriteLock() {
        return redisWriteLock == null ? RedisWriteLock.defaults() : redisWriteLock;
    }


    /**
     * 캐시 자동 적재 설정
     */
    public record AutoLoad(
        Boolean enabled,
        Set<String> exclude
    ) {

        /** 기본 자동 적재 설정 생성 */
        static AutoLoad defaults() {
            return new AutoLoad(true, Set.of());
        }

        /**
         * 자동 적재 활성 여부 반환
         *
         * @return 활성 여부
         */
        public boolean isEnabled() {
            return enabled == null || enabled;
        }

        /**
         * 자동 적재 제외 캐시 목록 반환
         *
         * @return 제외 캐시 목록
         */
        @Override
        public Set<String> exclude() {
            return exclude == null ? Set.of() : Collections.unmodifiableSet(exclude);
        }

        /**
         * 자동 적재 제외 여부 확인
         *
         * @param cacheName 캐시 이름
         * @return 제외 여부
         */
        public boolean isExcluded(String cacheName) {
            return exclude().stream()
                .anyMatch(excludedCacheName -> excludedCacheName != null
                    && excludedCacheName.equalsIgnoreCase(cacheName));
        }
    }


    /**
     * 캐시 스탬피드 방지 설정
     */
    public record Stampede(
        Duration lockTtl,
        Duration waitTimeout,
        Duration retryInterval
    ) {

        private static final Duration DEFAULT_LOCK_TTL = Duration.ofSeconds(5);
        private static final Duration DEFAULT_WAIT_TIMEOUT = Duration.ofSeconds(2);
        private static final Duration DEFAULT_RETRY_INTERVAL = Duration.ofMillis(100);

        /** 기본 스탬피드 방지 설정 생성 */
        static Stampede defaults() {
            return new Stampede(DEFAULT_LOCK_TTL, DEFAULT_WAIT_TIMEOUT, DEFAULT_RETRY_INTERVAL);
        }

        /**
         * 스탬피드 방지 락 TTL 반환
         *
         * @return 락 TTL
         */
        public Duration lockTtl() {
            return lockTtl == null ? DEFAULT_LOCK_TTL : lockTtl;
        }

        /**
         * 스탬피드 방지 대기 제한 시간 반환
         *
         * @return 대기 제한 시간
         */
        public Duration waitTimeout() {
            return waitTimeout == null ? DEFAULT_WAIT_TIMEOUT : waitTimeout;
        }

        /**
         * 스탬피드 방지 재시도 간격 반환
         *
         * @return 재시도 간격
         */
        public Duration retryInterval() {
            return retryInterval == null ? DEFAULT_RETRY_INTERVAL : retryInterval;
        }
    }


    /**
     * 캐시 적재 분산 락 설정
     */
    public record LoadLock(
        Boolean enabled,
        Duration lockAtMostFor,
        Duration lockAtLeastFor,
        Duration waitTimeout,
        Duration retryInterval
    ) {

        private static final Duration DEFAULT_LOCK_AT_MOST_FOR = Duration.ofMinutes(1);
        private static final Duration DEFAULT_LOCK_AT_LEAST_FOR = Duration.ZERO;
        private static final Duration DEFAULT_WAIT_TIMEOUT = Duration.ofSeconds(10);
        private static final Duration DEFAULT_RETRY_INTERVAL = Duration.ofMillis(200);

        /** 기본 캐시 적재 락 설정 생성 */
        static LoadLock defaults() {
            return new LoadLock(
                true,
                DEFAULT_LOCK_AT_MOST_FOR,
                DEFAULT_LOCK_AT_LEAST_FOR,
                DEFAULT_WAIT_TIMEOUT,
                DEFAULT_RETRY_INTERVAL
            );
        }

        /**
         * 캐시 적재 락 활성 여부 반환
         *
         * @return 활성 여부
         */
        public boolean isEnabled() {
            return enabled == null || enabled;
        }

        /**
         * 캐시 적재 락 최대 유지 시간 반환
         *
         * @return 최대 유지 시간
         */
        public Duration lockAtMostFor() {
            return lockAtMostFor == null ? DEFAULT_LOCK_AT_MOST_FOR : lockAtMostFor;
        }

        /**
         * 캐시 적재 락 최소 유지 시간 반환
         *
         * @return 최소 유지 시간
         */
        public Duration lockAtLeastFor() {
            return lockAtLeastFor == null ? DEFAULT_LOCK_AT_LEAST_FOR : lockAtLeastFor;
        }

        /**
         * 캐시 적재 락 대기 제한 시간 반환
         *
         * @return 대기 제한 시간
         */
        public Duration waitTimeout() {
            return waitTimeout == null ? DEFAULT_WAIT_TIMEOUT : waitTimeout;
        }

        /**
         * 캐시 적재 락 재시도 간격 반환
         *
         * @return 재시도 간격
         */
        public Duration retryInterval() {
            return retryInterval == null ? DEFAULT_RETRY_INTERVAL : retryInterval;
        }
    }

    
    /**
     * Redis 캐시 쓰기 락 설정
     */
    public record RedisWriteLock(
        Boolean enabled,
        Duration lockAtMostFor,
        Duration lockAtLeastFor
    ) {

        private static final Duration DEFAULT_LOCK_AT_MOST_FOR = Duration.ofMinutes(1);
        private static final Duration DEFAULT_LOCK_AT_LEAST_FOR = Duration.ZERO;

        /** 기본 Redis 쓰기 락 설정 생성 */
        static RedisWriteLock defaults() {
            return new RedisWriteLock(true, DEFAULT_LOCK_AT_MOST_FOR, DEFAULT_LOCK_AT_LEAST_FOR);
        }

        /**
         * Redis 쓰기 락 활성 여부 반환
         *
         * @return 활성 여부
         */
        public boolean isEnabled() {
            return enabled == null || enabled;
        }

        /**
         * Redis 쓰기 락 최대 유지 시간 반환
         *
         * @return 최대 유지 시간
         */
        public Duration lockAtMostFor() {
            return lockAtMostFor == null ? DEFAULT_LOCK_AT_MOST_FOR : lockAtMostFor;
        }

        /**
         * Redis 쓰기 락 최소 유지 시간 반환
         *
         * @return 최소 유지 시간
         */
        public Duration lockAtLeastFor() {
            return lockAtLeastFor == null ? DEFAULT_LOCK_AT_LEAST_FOR : lockAtLeastFor;
        }
    }
}
