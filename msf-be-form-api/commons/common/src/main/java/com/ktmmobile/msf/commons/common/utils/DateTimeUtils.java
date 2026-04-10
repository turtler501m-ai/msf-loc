package com.ktmmobile.msf.commons.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;

/**
 * 날짜/시간 변환 유틸 클래스
 * - LocalDate/LocalTime/LocalDateTime과 문자열(String)의 변환 시, 현재 유틸 클래스를 이용
 * - 현재 유틸 클래스를 이용하지 않는 변환 작업은 지양할 것
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeUtils {

    public static final DateTimeFormatter DEFAULT_DATE;
    public static final DateTimeFormatter DEFAULT_TIME;
    public static final DateTimeFormatter DEFAULT_DATE_TIME;
    public static final DateTimeFormatter CORE_SYSTEM_DATE;

    static {
        DEFAULT_DATE = new DateTimeFormatterBuilder()
            .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
            .appendLiteral('-')
            .appendValue(MONTH_OF_YEAR, 2)
            .appendLiteral('-')
            .appendValue(DAY_OF_MONTH, 2)
            .toFormatter();
        DEFAULT_TIME = new DateTimeFormatterBuilder()
            .appendValue(HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(MINUTE_OF_HOUR, 2)
            .optionalStart()
            .appendLiteral(':')
            .appendValue(SECOND_OF_MINUTE, 2)
            .toFormatter();
        DEFAULT_DATE_TIME = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DEFAULT_DATE)
            .appendLiteral('T')
            .append(DEFAULT_TIME)
            .toFormatter();
        // yyyyMMdd
        CORE_SYSTEM_DATE = new DateTimeFormatterBuilder()
            .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
            .appendValue(MONTH_OF_YEAR, 2)
            .appendValue(DAY_OF_MONTH, 2)
            .toFormatter();
    }


    public static String formattedDateTimeOf(LocalDateTime dateTime) {
        return dateTime.format(DEFAULT_DATE_TIME);
    }

    public static String formattedDateOf(LocalDate date) {
        return date.format(DEFAULT_DATE);
    }

    public static String formattedTimeOf(LocalTime time) {
        return time.format(DEFAULT_TIME);
    }

    public static String formattedDateTimeOfNow() {
        return formattedDateTimeOf(LocalDateTime.now());
    }

    public static String formattedDateOfNow() {
        return formattedDateOf(LocalDate.now());
    }

    public static String formattedTimeOfNow() {
        return formattedTimeOf(LocalTime.now());
    }

    public static LocalDateTime dateTimeOf(String dateTime) {
        return LocalDateTime.parse(dateTime, DEFAULT_DATE_TIME);
    }

    public static LocalDate dateOf(String date) {
        return LocalDate.parse(date, DEFAULT_DATE);
    }

    public static LocalTime timeOf(String time) {
        return LocalTime.parse(time, DEFAULT_TIME);
    }
}
