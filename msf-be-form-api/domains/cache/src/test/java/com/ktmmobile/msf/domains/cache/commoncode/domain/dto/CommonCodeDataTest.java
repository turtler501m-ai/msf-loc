package com.ktmmobile.msf.domains.cache.commoncode.domain.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommonCodeDataTest {

    @Test
    @DisplayName("Detail 문자열 accessor는 값이 null이면 기본값을 반환한다.")
    void detailStringAccessorReturnsDefaultValueWhenValueIsNull() {
        CommonCodeData.Detail detail = detail(null);

        assertThat(detail.abbrName("default-abbr")).isEqualTo("default-abbr");
        assertThat(detail.etcValue1("0")).isEqualTo("0");
        assertThat(detail.etcValue2("1")).isEqualTo("1");
        assertThat(detail.startDate("19000101")).isEqualTo("19000101");
        assertThat(detail.endDate("99991231")).isEqualTo("99991231");
    }

    @Test
    @DisplayName("Detail 문자열 accessor는 값이 빈 문자열이면 기본값을 반환한다.")
    void detailStringAccessorReturnsDefaultValueWhenValueIsBlank() {
        CommonCodeData.Detail detail = detail("");

        assertThat(detail.abbrName("default-abbr")).isEqualTo("default-abbr");
        assertThat(detail.etcValue1("0")).isEqualTo("0");
        assertThat(detail.etcValue2("1")).isEqualTo("1");
        assertThat(detail.startDate("19000101")).isEqualTo("19000101");
        assertThat(detail.endDate("99991231")).isEqualTo("99991231");
    }

    @Test
    @DisplayName("Detail 문자열 accessor는 값이 있으면 원본 값을 반환한다.")
    void detailStringAccessorReturnsOriginalValueWhenValueExists() {
        CommonCodeData.Detail detail = detail("value");

        assertThat(detail.abbrName("default-abbr")).isEqualTo("value");
        assertThat(detail.etcValue1("0")).isEqualTo("value");
        assertThat(detail.etcValue2("1")).isEqualTo("value");
        assertThat(detail.startDate("19000101")).isEqualTo("value");
        assertThat(detail.endDate("99991231")).isEqualTo("value");
    }

    @Test
    @DisplayName("Detail 숫자 accessor는 값이 빈 문자열이면 기본값을 반환한다.")
    void detailIntegerAccessorReturnsDefaultValueWhenValueIsBlank() {
        CommonCodeData.Detail detail = detail("");

        assertThat(detail.etcValue1AsInt(0)).isZero();
        assertThat(detail.etcValue2AsInt(1)).isOne();
        assertThat(detail.etcValue3AsInt(2)).isEqualTo(2);
        assertThat(detail.etcValue4AsInt(3)).isEqualTo(3);
        assertThat(detail.etcValue5AsInt(4)).isEqualTo(4);
        assertThat(detail.etcValue6AsInt(5)).isEqualTo(5);
    }

    @Test
    @DisplayName("Detail 숫자 accessor는 값이 있으면 int로 변환해 반환한다.")
    void detailIntegerAccessorReturnsParsedValueWhenValueExists() {
        CommonCodeData.Detail detail = detail("10");

        assertThat(detail.etcValue1AsInt(0)).isEqualTo(10);
        assertThat(detail.etcValue2AsInt(1)).isEqualTo(10);
        assertThat(detail.etcValue3AsInt(2)).isEqualTo(10);
        assertThat(detail.etcValue4AsInt(3)).isEqualTo(10);
        assertThat(detail.etcValue5AsInt(4)).isEqualTo(10);
        assertThat(detail.etcValue6AsInt(5)).isEqualTo(10);
    }

    private static CommonCodeData.Detail detail(String value) {
        return new CommonCodeData.Detail(
            value,
            value,
            value,
            value,
            value,
            0,
            value,
            value,
            value,
            value,
            value,
            value,
            value,
            value
        );
    }
}
