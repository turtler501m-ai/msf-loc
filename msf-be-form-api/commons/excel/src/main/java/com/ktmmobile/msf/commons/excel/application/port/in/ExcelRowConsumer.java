package com.ktmmobile.msf.commons.excel.application.port.in;

/**
 * 엑셀 단일 행 데이터 소비자
 *
 * @param <T> 행 데이터 타입
 */
@FunctionalInterface
public interface ExcelRowConsumer<T> {

    /**
     * 엑셀 행 데이터 처리
     *
     * @param row 엑셀 행 데이터
     */
    void accept(T row);
}
