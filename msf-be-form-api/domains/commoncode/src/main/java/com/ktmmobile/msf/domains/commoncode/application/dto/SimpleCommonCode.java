package com.ktmmobile.msf.domains.commoncode.application.dto;

public record SimpleCommonCode(
    String code,
    String title
) {

    public static SimpleCommonCode from(CommonCodeData data) {
        return of(data.code(), data.title());
    }

    public static SimpleCommonCode of(String code, String title) {
        return new SimpleCommonCode(code, title);
    }
}
