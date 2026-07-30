package com.ktmmobile.msf.commons.excel.support.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.excel.domain.vo.ExcelCellStyle;
import com.ktmmobile.msf.commons.excel.domain.vo.ExcelColumn;
import com.ktmmobile.msf.commons.excel.domain.vo.ExcelFile;
import com.ktmmobile.msf.commons.excel.domain.vo.ExcelSheet;
import com.ktmmobile.msf.commons.excel.support.properties.ExcelProperties;

/**
 * 엑셀 다운로드 유틸
 */
public final class ExcelDownloadUtils {

    private static final String XLSX_CONTENT_TYPE =
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final int DEFAULT_ROW_ACCESS_WINDOW_SIZE = 100;
    private static final int EXCEL_COLUMN_WIDTH_UNIT = 256;
    private static final int MAX_EXCEL_COLUMN_WIDTH = 255 * EXCEL_COLUMN_WIDTH_UNIT;

    private ExcelDownloadUtils() {
    }

    /**
     * 엑셀 파일 다운로드
     *
     * @param response HTTP 응답
     * @param excelFile 엑셀 파일
     * @param <T> 행 데이터 타입
     */
    public static <T> void download(HttpServletResponse response, ExcelFile<T> excelFile) {
        download(response, excelFile, ExcelProperties.defaults());
    }

    /**
     * 엑셀 파일 다운로드
     *
     * @param response HTTP 응답
     * @param excelFile 엑셀 파일
     * @param properties 엑셀 생성 설정
     * @param <T> 행 데이터 타입
     */
    public static <T> void download(HttpServletResponse response, ExcelFile<T> excelFile, ExcelProperties properties) {
        ExcelSheet<T> sheet = excelFile.sheet();
        try {
            download(response, excelFile.fileName(), sheet.sheetName(), sheet.columns(), sheet.data(), propertiesOrDefault(properties));
        } catch (IOException e) {
            throw new SimpleDomainException("엑셀 파일을 다운로드할 수 없습니다.", e);
        }
    }

    /**
     * Iterable 데이터 엑셀 다운로드
     *
     * @param response HTTP 응답
     * @param fileName 다운로드 파일명
     * @param sheetName 시트명
     * @param columns 엑셀 컬럼 정의 목록
     * @param data 엑셀 행 데이터 Iterable
     * @param <T> 행 데이터 타입
     */
    private static <T> void download(
        HttpServletResponse response,
        String fileName,
        String sheetName,
        List<ExcelColumn<T>> columns,
        Iterable<T> data,
        ExcelProperties properties
    ) throws IOException {
        setDownloadHeaders(response, fileName);

        try (SXSSFWorkbook workbook = build(sheetName, columns, data, properties)) {
            workbook.write(response.getOutputStream());
            response.flushBuffer();
        }
    }

    /**
     * 스트리밍 엑셀 워크북 생성
     *
     * @param sheetName 시트명
     * @param columns 엑셀 컬럼 정의 목록
     * @param data 엑셀 행 데이터 Iterable
     * @param <T> 행 데이터 타입
     * @return 스트리밍 엑셀 워크북
     */
    public static <T> SXSSFWorkbook build(String sheetName, List<ExcelColumn<T>> columns, Iterable<T> data) {
        return build(sheetName, columns, data, ExcelProperties.defaults());
    }

    /**
     * 스트리밍 엑셀 워크북 생성
     *
     * @param sheetName 시트명
     * @param columns 엑셀 컬럼 정의 목록
     * @param data 엑셀 행 데이터 Iterable
     * @param properties 엑셀 생성 설정
     * @param <T> 행 데이터 타입
     * @return 스트리밍 엑셀 워크북
     */
    public static <T> SXSSFWorkbook build(
        String sheetName,
        List<ExcelColumn<T>> columns,
        Iterable<T> data,
        ExcelProperties properties
    ) {
        ExcelProperties excelProperties = propertiesOrDefault(properties);
        SXSSFWorkbook workbook = new SXSSFWorkbook(DEFAULT_ROW_ACCESS_WINDOW_SIZE);
        workbook.setCompressTempFiles(true);

        Sheet sheet = workbook.createSheet(sheetName);
        applyColumnWidths(sheet, columns);
        writeHeader(workbook, sheet, columns, excelProperties);
        writeRows(workbook, sheet, columns, data);

        return workbook;
    }

    /**
     * 엑셀 다운로드 응답 헤더 설정
     *
     * @param response HTTP 응답
     * @param fileName 다운로드 파일명
     */
    private static void setDownloadHeaders(HttpServletResponse response, String fileName) {
        String encoded = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replace("+", "%20");
        response.setContentType(XLSX_CONTENT_TYPE);
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encoded);
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
    }

    /**
     * 헤더 행 작성
     *
     * @param sheet 엑셀 시트
     * @param columns 엑셀 컬럼 정의 목록
     * @param <T> 행 데이터 타입
     */
    private static <T> void writeHeader(
        SXSSFWorkbook workbook,
        Sheet sheet,
        List<ExcelColumn<T>> columns,
        ExcelProperties properties
    ) {
        Row header = sheet.createRow(0);
        CellStyle headerStyle = createHeaderStyle(workbook, properties);
        for (int i = 0; i < columns.size(); i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columns.get(i).header());
            cell.setCellStyle(headerStyle);
        }
    }

    /**
     * 컬럼 너비 적용
     *
     * @param sheet 엑셀 시트
     * @param columns 엑셀 컬럼 정의 목록
     * @param <T> 행 데이터 타입
     */
    private static <T> void applyColumnWidths(Sheet sheet, List<ExcelColumn<T>> columns) {
        for (int i = 0; i < columns.size(); i++) {
            Integer width = columns.get(i).width();
            if (width == null) {
                continue;
            }
            sheet.setColumnWidth(i, Math.min(width * EXCEL_COLUMN_WIDTH_UNIT, MAX_EXCEL_COLUMN_WIDTH));
        }
    }

    /**
     * 헤더 셀 스타일 생성
     *
     * @param workbook 엑셀 워크북
     * @param properties 엑셀 생성 설정
     * @return 헤더 셀 스타일
     */
    private static CellStyle createHeaderStyle(SXSSFWorkbook workbook, ExcelProperties properties) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(headerBackgroundColor(properties).getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return headerStyle;
    }

    /**
     * 설정 기반 헤더 배경색 반환
     *
     * @param properties 엑셀 생성 설정
     * @return 헤더 배경색
     */
    private static IndexedColors headerBackgroundColor(ExcelProperties properties) {
        String backgroundColor = properties.headerRow().backgroundColor()
            .trim()
            .replace('-', '_')
            .toUpperCase();
        return IndexedColors.valueOf(backgroundColor);
    }

    /**
     * 엑셀 생성 설정 기본값 보완
     *
     * @param properties 엑셀 생성 설정
     * @return 엑셀 생성 설정
     */
    private static ExcelProperties propertiesOrDefault(ExcelProperties properties) {
        return properties == null ? ExcelProperties.defaults() : properties;
    }

    /**
     * 데이터 행 작성
     *
     * @param sheet 엑셀 시트
     * @param columns 엑셀 컬럼 정의 목록
     * @param data 엑셀 행 데이터 Iterable
     * @param <T> 행 데이터 타입
     */
    private static <T> void writeRows(SXSSFWorkbook workbook, Sheet sheet, List<ExcelColumn<T>> columns, Iterable<T> data) {
        Map<ExcelCellStyle, CellStyle> cellStyles = createCellStyles(workbook, columns);
        int rowIdx = 1;
        for (T item: data) {
            Row row = sheet.createRow(rowIdx);
            int rowNumber = rowIdx;
            rowIdx++;
            for (int i = 0; i < columns.size(); i++) {
                ExcelColumn<T> column = columns.get(i);
                Cell cell = row.createCell(i);
                ExcelCellStyle columnCellStyle = column.cellStyle();
                setCellStyle(cell, cellStyles.get(columnCellStyle));
                setCellValue(cell, column.extractValue(item, rowNumber), columnCellStyle);
            }
        }
    }

    /**
     * 컬럼별 셀 스타일 생성
     *
     * @param workbook 엑셀 워크북
     * @param columns 엑셀 컬럼 정의 목록
     * @param <T> 행 데이터 타입
     * @return 셀 스타일 맵
     */
    private static <T> Map<ExcelCellStyle, CellStyle> createCellStyles(SXSSFWorkbook workbook, List<ExcelColumn<T>> columns) {
        Map<ExcelCellStyle, CellStyle> cellStyles = new HashMap<>();
        DataFormat dataFormat = workbook.createDataFormat();
        for (ExcelColumn<T> column: columns) {
            ExcelCellStyle style = column.cellStyle();
            if (!style.hasDataFormat() || cellStyles.containsKey(style)) {
                continue;
            }
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setDataFormat(dataFormat.getFormat(style.dataFormat()));
            cellStyles.put(style, cellStyle);
        }
        return cellStyles;
    }

    /**
     * 셀 스타일 설정
     *
     * @param cell 엑셀 셀
     * @param cellStyle 셀 스타일
     */
    private static void setCellStyle(Cell cell, CellStyle cellStyle) {
        if (cellStyle != null) {
            cell.setCellStyle(cellStyle);
        }
    }

    /**
     * 셀 값 설정
     *
     * @param cell 엑셀 셀
     * @param value 셀 값
     * @param cellStyle 셀 스타일
     */
    private static void setCellValue(Cell cell, Object value, ExcelCellStyle cellStyle) {
        if (ExcelCellStyle.text().equals(cellStyle) && value != null) {
            cell.setCellValue(value.toString());
            return;
        }
        switch (value) {
            case null -> cell.setBlank();
            case Number number -> cell.setCellValue(number.doubleValue());
            case Boolean bool -> cell.setCellValue(bool);
            case Date date -> cell.setCellValue(date);
            case LocalDate date -> cell.setCellValue(date);
            case LocalDateTime dateTime -> cell.setCellValue(dateTime);
            case LocalTime time -> cell.setCellValue(time.toString());
            default -> cell.setCellValue(value.toString());
        }
    }
}
