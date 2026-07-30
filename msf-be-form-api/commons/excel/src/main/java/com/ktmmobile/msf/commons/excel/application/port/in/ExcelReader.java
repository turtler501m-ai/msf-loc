package com.ktmmobile.msf.commons.excel.application.port.in;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.ktmmobile.msf.commons.excel.domain.vo.ExcelReadOption;
import com.ktmmobile.msf.commons.excel.domain.vo.ExcelRowMapper;
import com.ktmmobile.msf.commons.excel.domain.vo.ExcelStreamingRowMapper;

/**
 * 엑셀 파일 읽기 유스케이스
 */
public interface ExcelReader {

    /**
     * 기본 옵션으로 엑셀 파일 읽기
     *
     * @param inputStream 엑셀 바이너리 입력 스트림
     * @param rowMapper 행 데이터 변환기
     * @param <T> 행 데이터 타입
     * @return 행 데이터 목록
     */
    <T> List<T> read(InputStream inputStream, ExcelRowMapper<T> rowMapper) throws IOException;

    /**
     * 지정 옵션으로 엑셀 파일 읽기
     *
     * @param inputStream 엑셀 바이너리 입력 스트림
     * @param option 엑셀 읽기 옵션
     * @param rowMapper 행 데이터 변환기
     * @param <T> 행 데이터 타입
     * @return 행 데이터 목록
     */
    <T> List<T> read(InputStream inputStream, ExcelReadOption option, ExcelRowMapper<T> rowMapper) throws IOException;

    /**
     * 기본 옵션으로 엑셀 파일 스트리밍 읽기
     *
     * @param inputStream 엑셀 바이너리 입력 스트림
     * @param rowMapper 행 데이터 변환기
     * @param rowConsumer 행 데이터 소비자
     * @param <T> 행 데이터 타입
     */
    <T> void stream(InputStream inputStream, ExcelStreamingRowMapper<T> rowMapper, ExcelRowConsumer<T> rowConsumer)
        throws IOException;

    /**
     * 지정 옵션으로 엑셀 파일 스트리밍 읽기
     *
     * @param inputStream 엑셀 바이너리 입력 스트림
     * @param option 엑셀 읽기 옵션
     * @param rowMapper 행 데이터 변환기
     * @param rowConsumer 행 데이터 소비자
     * @param <T> 행 데이터 타입
     */
    <T> void stream(
        InputStream inputStream,
        ExcelReadOption option,
        ExcelStreamingRowMapper<T> rowMapper,
        ExcelRowConsumer<T> rowConsumer
    ) throws IOException;
}
