package com.ktmmobile.msf.external.websecurity.web.formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

import org.springframework.format.Formatter;

import com.ktmmobile.msf.commons.common.utils.DateTimeUtils;

public class CustomLocalDateFormatter implements Formatter<LocalDate> {

    @Override
    public LocalDate parse(String date, Locale locale) throws ParseException {
        return DateTimeUtils.dateOf(date);
    }

    @Override
    public String print(LocalDate localDate, Locale locale) {
        return DateTimeUtils.formattedDateOf(localDate);
    }
}
