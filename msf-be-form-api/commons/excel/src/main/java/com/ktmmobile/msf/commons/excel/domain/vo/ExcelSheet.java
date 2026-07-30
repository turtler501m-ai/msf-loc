package com.ktmmobile.msf.commons.excel.domain.vo;

import java.util.List;

/**
 * 엑셀 시트 정의
 *
 * @param sheetName 시트명
 * @param columns 엑셀 컬럼 정의 목록
 * @param data 엑셀 행 데이터 Iterable
 * @param <T> 행 데이터 타입
 */
public record ExcelSheet<T>(String sheetName, List<ExcelColumn<T>> columns, Iterable<T> data) {

    /**
     * 엑셀 시트 정의 생성
     *
     * @param sheetName 시트명
     * @param columns 엑셀 컬럼 정의 목록
     * @param data 엑셀 행 데이터 Iterable
     * @param <T> 행 데이터 타입
     * @return 엑셀 시트 정의
     */
    public static <T> ExcelSheet<T> of(String sheetName, List<ExcelColumn<T>> columns, Iterable<T> data) {
        return new ExcelSheet<>(sheetName, columns, data);
    }
}
