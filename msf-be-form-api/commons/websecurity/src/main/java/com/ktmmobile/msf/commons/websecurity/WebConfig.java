package com.ktmmobile.msf.commons.websecurity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ktmmobile.msf.commons.common.commonenum.converter.CommonEnumConverterFactory;
import com.ktmmobile.msf.commons.websecurity.web.formatter.CustomLocalDateFormatter;
import com.ktmmobile.msf.commons.websecurity.web.formatter.CustomLocalDateTimeFormatter;
import com.ktmmobile.msf.commons.websecurity.web.formatter.CustomLocalTimeFormatter;


@Configuration(proxyBeanMethods = false)
public class WebConfig implements WebMvcConfigurer {

    private static final String[] ALLOWED_ORIGINS = {
    };

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(ALLOWED_ORIGINS);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(LocalDate.class, new CustomLocalDateFormatter());
        registry.addFormatterForFieldType(LocalTime.class, new CustomLocalTimeFormatter());
        registry.addFormatterForFieldType(LocalDateTime.class, new CustomLocalDateTimeFormatter());

        registry.addConverterFactory(new CommonEnumConverterFactory<>());
    }
}
