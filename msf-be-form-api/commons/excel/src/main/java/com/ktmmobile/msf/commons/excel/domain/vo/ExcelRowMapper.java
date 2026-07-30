package com.ktmmobile.msf.commons.excel.domain.vo;

import org.apache.poi.ss.usermodel.Row;

/**
 * 엑셀 행 데이터 변환기
 *
 * @param <T> 행 데이터 타입
 */
@FunctionalInterface
public interface ExcelRowMapper<T> {

    /**
     * 엑셀 행 데이터 변환
     *
     * @param row 엑셀 행
     * @param rowIndex 행 인덱스
     * @return 변환된 행 데이터
     */
    T map(Row row, int rowIndex);
}
