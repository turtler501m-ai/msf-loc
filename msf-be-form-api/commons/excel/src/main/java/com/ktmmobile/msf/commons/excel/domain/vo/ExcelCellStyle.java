package com.ktmmobile.msf.commons.excel.domain.vo;

/**
 * 엑셀 셀 스타일 정의
 *
 * @param dataFormat 엑셀 데이터 포맷
 */
public record ExcelCellStyle(String dataFormat) {

    private static final ExcelCellStyle NONE = new ExcelCellStyle(null);

    /**
     * 스타일 없음
     *
     * @return 스타일 없음
     */
    public static ExcelCellStyle none() {
        return NONE;
    }

    /**
     * 숫자 포맷
     *
     * @return 숫자 포맷
     */
    public static ExcelCellStyle number() {
        return dataFormat("0");
    }

    /**
     * 소수 숫자 포맷
     *
     * @return 소수 숫자 포맷
     */
    public static ExcelCellStyle decimal() {
        return dataFormat("0.00");
    }

    /**
     * 콤마 포함 숫자 포맷
     *
     * @return 콤마 포함 숫자 포맷
     */
    public static ExcelCellStyle numberComma() {
        return dataFormat("#,##0");
    }

    /**
     * 콤마 포함 소수 숫자 포맷
     *
     * @return 콤마 포함 소수 숫자 포맷
     */
    public static ExcelCellStyle decimalComma() {
        return dataFormat("#,##0.00");
    }

    /**
     * 백분율 포맷
     *
     * @return 백분율 포맷
     */
    public static ExcelCellStyle percent() {
        return dataFormat("0.00%");
    }

    /**
     * 날짜 포맷
     *
     * @return 날짜 포맷
     */
    public static ExcelCellStyle date() {
        return dataFormat("yyyy-mm-dd");
    }

    /**
     * 일시 포맷
     *
     * @return 일시 포맷
     */
    public static ExcelCellStyle dateTime() {
        return dataFormat("yyyy-mm-dd hh:mm:ss");
    }

    /**
     * 텍스트 포맷
     *
     * @return 텍스트 포맷
     */
    public static ExcelCellStyle text() {
        return dataFormat("@");
    }

    /**
     * 사용자 지정 데이터 포맷
     *
     * @param dataFormat 엑셀 데이터 포맷
     * @return 셀 스타일
     */
    public static ExcelCellStyle dataFormat(String dataFormat) {
        return new ExcelCellStyle(dataFormat);
    }

    /**
     * 데이터 포맷 보유 여부
     *
     * @return 데이터 포맷 보유 여부
     */
    public boolean hasDataFormat() {
        return dataFormat != null && !dataFormat.isBlank();
    }
}
