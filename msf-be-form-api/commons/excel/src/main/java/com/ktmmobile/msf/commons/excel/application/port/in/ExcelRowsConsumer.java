package com.ktmmobile.msf.commons.excel.application.port.in;

/**
 * 엑셀 행 데이터 소비자
 *
 * @param <T> 행 데이터 타입
 */
@FunctionalInterface
public interface ExcelRowsConsumer<T> {

    /**
     * 엑셀 행 데이터 처리
     *
     * @param rows 엑셀 행 데이터 Iterable
     */
    void accept(Iterable<T> rows);
}
