package com.ktmmobile.msf.commons.excel.domain.vo;

/**
 * 엑셀 읽기 옵션
 *
 * @param sheetIndex 시트 인덱스
 * @param startRowIndex 읽기 시작 행 인덱스
 */
public record ExcelReadOption(int sheetIndex, int startRowIndex) {

    /**
     * 기본 읽기 옵션 생성
     *
     * @return 첫 번째 시트의 헤더 다음 행부터 읽는 옵션
     */
    public static ExcelReadOption defaultOption() {
        return new ExcelReadOption(0, 1);
    }

    /**
     * 첫 번째 시트 읽기 옵션 생성
     *
     * @param startRowIndex 읽기 시작 행 인덱스
     * @return 첫 번째 시트 읽기 옵션
     */
    public static ExcelReadOption firstSheet(int startRowIndex) {
        return new ExcelReadOption(0, startRowIndex);
    }
}
