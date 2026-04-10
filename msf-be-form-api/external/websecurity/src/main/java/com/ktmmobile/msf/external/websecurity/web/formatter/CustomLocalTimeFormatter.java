package com.ktmmobile.msf.external.websecurity.web.formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Locale;

import org.springframework.format.Formatter;

import com.ktmmobile.msf.commons.common.utils.DateTimeUtils;

public class CustomLocalTimeFormatter implements Formatter<LocalTime> {

    @Override
    public LocalTime parse(String time, Locale locale) throws ParseException {
        return DateTimeUtils.timeOf(time);
    }

    @Override
    public String print(LocalTime localTime, Locale locale) {
        return DateTimeUtils.formattedTimeOf(localTime);
    }
}
