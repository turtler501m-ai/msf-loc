package com.ktmmobile.msf.external.websecurity.web.formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.format.Formatter;

import com.ktmmobile.msf.commons.common.utils.DateTimeUtils;

public class CustomLocalDateTimeFormatter implements Formatter<LocalDateTime> {

    @Override
    public LocalDateTime parse(String dateTime, Locale locale) throws ParseException {
        return DateTimeUtils.dateTimeOf(dateTime);
    }

    @Override
    public String print(LocalDateTime localDateTime, Locale locale) {
        return DateTimeUtils.formattedDateTimeOf(localDateTime);
    }
}
