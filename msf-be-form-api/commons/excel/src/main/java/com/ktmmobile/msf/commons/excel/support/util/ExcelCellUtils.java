package com.ktmmobile.msf.commons.excel.support.util;

import java.time.LocalDateTime;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

/**
 * 엑셀 셀 값 조회 유틸
 */
public final class ExcelCellUtils {

    private ExcelCellUtils() {
    }

    /**
     * 문자열 셀 값 조회
     *
     * @param row 엑셀 행
     * @param cellIndex 셀 인덱스
     * @return 문자열 셀 값
     */
    public static String stringValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            return "";
        }
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> numericStringValue(cell);
            case BOOLEAN -> Boolean.toString(cell.getBooleanCellValue());
            case FORMULA -> formulaStringValue(cell);
            case BLANK, ERROR, _NONE -> "";
        };
    }

    /**
     * 숫자 셀 값 조회
     *
     * @param row 엑셀 행
     * @param cellIndex 셀 인덱스
     * @return 숫자 셀 값
     */
    public static Double numberValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        }
        String value = stringValue(row, cellIndex);
        if (value.isBlank()) {
            return null;
        }
        return Double.valueOf(value);
    }

    /**
     * 일시 셀 값 조회
     *
     * @param row 엑셀 행
     * @param cellIndex 셀 인덱스
     * @return 일시 셀 값
     */
    public static LocalDateTime dateTimeValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null || cell.getCellType() != CellType.NUMERIC || !DateUtil.isCellDateFormatted(cell)) {
            return null;
        }
        return cell.getLocalDateTimeCellValue();
    }

    /**
     * 수식 셀 문자열 값 조회
     *
     * @param cell 엑셀 셀
     * @return 수식 결과 문자열 값
     */
    private static String formulaStringValue(Cell cell) {
        return switch (cell.getCachedFormulaResultType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> numericStringValue(cell);
            case BOOLEAN -> Boolean.toString(cell.getBooleanCellValue());
            case BLANK, ERROR, FORMULA, _NONE -> "";
        };
    }

    /**
     * 숫자 셀 문자열 값 조회
     *
     * @param cell 엑셀 셀
     * @return 숫자 셀 문자열 값
     */
    private static String numericStringValue(Cell cell) {
        double value = cell.getNumericCellValue();
        if (DateUtil.isCellDateFormatted(cell)) {
            return cell.getLocalDateTimeCellValue().toString();
        }
        if (value == Math.rint(value)) {
            return Long.toString((long) value);
        }
        return Double.toString(value);
    }
}
