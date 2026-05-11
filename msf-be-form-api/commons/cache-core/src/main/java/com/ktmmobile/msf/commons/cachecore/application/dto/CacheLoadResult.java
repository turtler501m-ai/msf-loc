package com.ktmmobile.msf.commons.cachecore.application.dto;

import java.time.Duration;
import java.time.Instant;

import com.ktmmobile.msf.commons.cachecore.domain.code.CacheLoadStatus;

/**
 * 캐시 적재 실행 결과 표현
 */
public record CacheLoadResult(
    String cacheName,
    CacheLoadStatus status,
    int count,
    Instant startedAt,
    Instant finishedAt,
    String message
) {

    /**
     * 캐시 적재 성공 결과 생성
     *
     * @param cacheName 캐시 이름
     * @param count 적재 건수
     * @param startedAt 적재 시작 시각
     * @param finishedAt 적재 종료 시각
     * @return 성공 결과
     */
    public static CacheLoadResult success(String cacheName, int count, Instant startedAt, Instant finishedAt) {
        return new CacheLoadResult(cacheName, CacheLoadStatus.SUCCESS, count, startedAt, finishedAt, null);
    }

    /**
     * 캐시 적재 생략 결과 생성
     *
     * @param cacheName 캐시 이름
     * @param message 생략 메시지
     * @return 생략 결과
     */
    public static CacheLoadResult skipped(String cacheName, String message) {
        Instant now = Instant.now();
        return new CacheLoadResult(cacheName, CacheLoadStatus.SKIPPED, 0, now, now, message);
    }

    /**
     * 캐시 적재 소요 시간 반환
     *
     * @return 적재 소요 시간
     */
    public Duration elapsed() {
        return Duration.between(startedAt, finishedAt);
    }
}
