package com.ktmmobile.msf.domains.cache.commoncode.application.dto;

import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeData;

/**
 * 코드와 코드명만 필요한 화면/응답용 단순 공통코드
 */
public record SimpleCommonCode(
    String code,
    String title
) {

    /** 공통코드 데이터의 단순 공통코드 변환 */
    public static SimpleCommonCode from(CommonCodeData data) {
        return of(data.code(), data.title());
    }

    /** 단순 공통코드 생성 */
    public static SimpleCommonCode of(String code, String title) {
        return new SimpleCommonCode(code, title);
    }

    /** 빈 단순 공통코드 생성 */
    public static SimpleCommonCode empty() {
        return new SimpleCommonCode("", "");
    }
}
