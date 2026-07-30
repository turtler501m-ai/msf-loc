package com.ktmmobile.msf.commons.cachecore.support.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.springframework.stereotype.Component;

/**
 * 캐시 적재 시간 포맷 변환기
 */
@Component
public class CacheLoadTimeFormatter {

    private static final DateTimeFormatter REDIS_LOAD_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** Instant를 시스템 기본 시간대 LocalDateTime으로 변환 */
    public LocalDateTime toLocalDateTime(Instant loadTime) {
        return LocalDateTime.ofInstant(loadTime, ZoneId.systemDefault())
            .withNano(0);
    }

    /** 캐시 적재 시간을 문자열로 변환 */
    public String format(LocalDateTime loadTime) {
        return loadTime.withNano(0)
            .format(REDIS_LOAD_TIME_FORMATTER);
    }

    /** 캐시 적재 시간 문자열 파싱 */
    public Optional<LocalDateTime> parse(String loadTime) {
        if (loadTime.isBlank()) {
            return Optional.empty();
        }
        try {
            return Optional.of(parseRedisLoadTime(loadTime));
        } catch (DateTimeParseException _) {
            return Optional.of(parseIsoLoadTime(loadTime));
        }
    }

    private LocalDateTime parseRedisLoadTime(String loadTime) {
        return LocalDateTime.parse(loadTime, REDIS_LOAD_TIME_FORMATTER);
    }

    private LocalDateTime parseIsoLoadTime(String loadTime) {
        try {
            return toLocalDateTime(Instant.parse(loadTime));
        } catch (DateTimeParseException _) {
            return LocalDateTime.parse(loadTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }
}
