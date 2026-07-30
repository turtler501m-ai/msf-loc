package com.ktmmobile.msf.commons.excel.domain.vo;

import java.util.Map;

/**
 * 스트리밍 엑셀 행 데이터
 *
 * @param rowIndex 행 인덱스
 * @param values 셀 값 맵
 */
public record ExcelRow(int rowIndex, Map<Integer, String> values) {

    /**
     * 엑셀 표시 행 번호 조회
     *
     * @return 엑셀 표시 행 번호
     */
    public int rowNum() {
        return rowIndex + 1;
    }

    /**
     * 문자열 셀 값 조회
     *
     * @param cellIndex 셀 인덱스
     * @return 문자열 셀 값
     */
    public String stringValue(int cellIndex) {
        return values.getOrDefault(cellIndex, "");
    }

    /**
     * 빈 행 여부
     *
     * @return 빈 행 여부
     */
    public boolean isEmpty() {
        return values.values().stream().allMatch(value -> value == null || value.isBlank());
    }
}
