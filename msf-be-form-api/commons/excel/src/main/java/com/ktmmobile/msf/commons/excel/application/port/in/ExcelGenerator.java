package com.ktmmobile.msf.commons.excel.application.port.in;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.ktmmobile.msf.commons.excel.domain.vo.ExcelColumn;
import com.ktmmobile.msf.commons.excel.domain.vo.ExcelSheet;

/**
 * 엑셀 파일 생성 유스케이스
 */
public interface ExcelGenerator {

    /**
     * 엑셀 워크북 생성
     *
     * @param sheet 엑셀 시트
     * @param <T> 행 데이터 타입
     * @return 스트리밍 엑셀 워크북
     */
    <T> SXSSFWorkbook generate(ExcelSheet<T> sheet);

    /**
     * 엑셀 워크북 생성
     *
     * @param sheetName 시트명
     * @param columns 엑셀 컬럼 정의 목록
     * @param data 엑셀 행 데이터 Iterable
     * @param <T> 행 데이터 타입
     * @return 스트리밍 엑셀 워크북
     */
    <T> SXSSFWorkbook generate(String sheetName, List<ExcelColumn<T>> columns, Iterable<T> data);

    /**
     * 엑셀 파일 출력
     *
     * @param outputStream 출력 스트림
     * @param sheet 엑셀 시트
     * @param <T> 행 데이터 타입
     */
    <T> void write(OutputStream outputStream, ExcelSheet<T> sheet) throws IOException;
}
