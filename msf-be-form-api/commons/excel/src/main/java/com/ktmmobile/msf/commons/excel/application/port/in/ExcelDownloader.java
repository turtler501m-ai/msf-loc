package com.ktmmobile.msf.commons.excel.application.port.in;

import jakarta.servlet.http.HttpServletResponse;

import com.ktmmobile.msf.commons.excel.domain.vo.ExcelFile;

/**
 * 엑셀 다운로드 유스케이스
 */
public interface ExcelDownloader {

    /**
     * 엑셀 파일 다운로드
     *
     * @param response HTTP 응답
     * @param excelFile 엑셀 파일
     * @param <T> 행 데이터 타입
     */
    <T> void download(HttpServletResponse response, ExcelFile<T> excelFile);

    /**
     * 엑셀 파일 다운로드 후 접속 이력 기록
     *
     * @param response HTTP 응답
     * @param excelFile 엑셀 파일
     * @param processContent 처리 내용
     * @param resultContent 처리 결과 내용
     * @param <T> 행 데이터 타입
     */
    <T> void download(
        HttpServletResponse response,
        ExcelFile<T> excelFile,
        String processContent,
        String resultContent
    );

}
