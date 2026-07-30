package com.ktmmobile.msf.commons.excel.application.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.excel.application.port.in.ExcelGenerator;
import com.ktmmobile.msf.commons.excel.domain.vo.ExcelColumn;
import com.ktmmobile.msf.commons.excel.domain.vo.ExcelSheet;
import com.ktmmobile.msf.commons.excel.support.util.ExcelDownloadUtils;

/**
 * 스트리밍 엑셀 파일 생성 서비스
 */
@Service
public class StreamingExcelGenerateService implements ExcelGenerator {

    /**
     * 엑셀 시트 기준 스트리밍 워크북 생성
     *
     * @param sheet 엑셀 시트
     * @param <T> 행 데이터 타입
     * @return 스트리밍 엑셀 워크북
     */
    @Override
    public <T> SXSSFWorkbook generate(ExcelSheet<T> sheet) {
        return generate(sheet.sheetName(), sheet.columns(), sheet.data());
    }

    /**
     * 시트명, 컬럼, 행 데이터 기준 스트리밍 워크북 생성
     *
     * @param sheetName 시트명
     * @param columns 엑셀 컬럼 정의 목록
     * @param data 엑셀 행 데이터 Iterable
     * @param <T> 행 데이터 타입
     * @return 스트리밍 엑셀 워크북
     */
    @Override
    public <T> SXSSFWorkbook generate(String sheetName, List<ExcelColumn<T>> columns, Iterable<T> data) {
        return ExcelDownloadUtils.build(sheetName, columns, data);
    }

    /**
     * 엑셀 워크북 출력
     *
     * @param outputStream 출력 스트림
     * @param sheet 엑셀 시트
     * @param <T> 행 데이터 타입
     */
    @Override
    public <T> void write(OutputStream outputStream, ExcelSheet<T> sheet) throws IOException {
        try (SXSSFWorkbook workbook = generate(sheet)) {
            workbook.write(outputStream);
        }
    }
}
