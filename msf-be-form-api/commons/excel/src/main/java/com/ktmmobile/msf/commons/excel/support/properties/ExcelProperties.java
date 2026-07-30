package com.ktmmobile.msf.commons.excel.support.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

/**
 * 엑셀 생성 설정
 *
 * @param headerRow 헤더 행 설정
 */
@ConfigurationProperties(prefix = "excel")
public record ExcelProperties(
    HeaderRow headerRow
) {

    private static final String DEFAULT_HEADER_BACKGROUND_COLOR = "GREY_25_PERCENT";

    public ExcelProperties {
        headerRow = headerRow == null ? HeaderRow.defaults() : headerRow;
    }

    /**
     * 기본 엑셀 생성 설정
     */
    public static ExcelProperties defaults() {
        return new ExcelProperties(HeaderRow.defaults());
    }

    /**
     * 엑셀 헤더 행 설정
     *
     * @param backgroundColor 헤더 행 배경색
     */
    public record HeaderRow(
        String backgroundColor
    ) {

        public HeaderRow {
            backgroundColor = StringUtils.hasText(backgroundColor)
                ? backgroundColor
                : DEFAULT_HEADER_BACKGROUND_COLOR;
        }

        /**
         * 기본 헤더 행 설정
         */
        public static HeaderRow defaults() {
            return new HeaderRow(DEFAULT_HEADER_BACKGROUND_COLOR);
        }
    }
}
