package com.ktmmobile.msf.domains.form.main.application.dto;

public record ChartCountResponse(
    String code,
    String name,
    Integer count
) {
    public static ChartCountResponse of(String code, String name, Integer count) {
        return new ChartCountResponse(code, name, count);
    }
}
