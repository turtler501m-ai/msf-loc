package com.ktmmobile.msf.commons.excel.domain.vo;

import java.util.List;

/**
 * 엑셀 파일 정의
 *
 * @param fileName 다운로드 파일명
 * @param sheet 엑셀 시트
 * @param <T> 행 데이터 타입
 */
public record ExcelFile<T>(String fileName, ExcelSheet<T> sheet) {

    /**
     * 엑셀 파일 정의 생성
     *
     * @param fileName 다운로드 파일명
     * @param sheetName 시트명
     * @param columns 엑셀 컬럼 정의 목록
     * @param data 엑셀 행 데이터 Iterable
     * @param <T> 행 데이터 타입
     * @return 엑셀 파일 정의
     */
    public static <T> ExcelFile<T> of(
        String fileName,
        String sheetName,
        List<ExcelColumn<T>> columns,
        Iterable<T> data
    ) {
        return new ExcelFile<>(fileName, ExcelSheet.of(sheetName, columns, data));
    }
}
