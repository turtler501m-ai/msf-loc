package com.ktmmobile.msf.commons.excel.domain.vo;

/**
 * 스트리밍 엑셀 행 데이터 변환기
 *
 * @param <T> 행 데이터 타입
 */
@FunctionalInterface
public interface ExcelStreamingRowMapper<T> {

    /**
     * 스트리밍 엑셀 행 데이터 변환
     *
     * @param row 엑셀 행
     * @return 변환된 행 데이터
     */
    T map(ExcelRow row);
}
