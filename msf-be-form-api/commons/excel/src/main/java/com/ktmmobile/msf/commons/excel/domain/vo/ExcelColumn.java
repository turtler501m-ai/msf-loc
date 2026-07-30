package com.ktmmobile.msf.commons.excel.domain.vo;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 엑셀 컬럼 정의
 *
 * @param header 헤더명
 * @param valueExtractor 행 데이터 값 추출기
 * @param indexedValueExtractor 행 번호 포함 데이터 값 추출기
 * @param cellStyle 셀 스타일 정의
 * @param width 열 너비
 * @param <T> 행 데이터 타입
 */
public record ExcelColumn<T>(
    String header,
    Function<T, Object> valueExtractor,
    BiFunction<T, Integer, Object> indexedValueExtractor,
    ExcelCellStyle cellStyle,
    Integer width
) {

    private static final int MAX_WIDTH = 255;

    public ExcelColumn {
        if (valueExtractor == null && indexedValueExtractor == null) {
            throw new IllegalArgumentException("Excel column value extractor must not be null");
        }
        if (cellStyle == null) {
            cellStyle = ExcelCellStyle.none();
        }
        if (width != null && width <= 0) {
            throw new IllegalArgumentException("Excel column width must be greater than 0");
        }
        if (width != null && width > MAX_WIDTH) {
            throw new IllegalArgumentException("Excel column width must be less than or equal to 255");
        }
    }

    /**
     * 기본 엑셀 컬럼 정의 생성자
     *
     * @param header 헤더명
     * @param valueExtractor 행 데이터 값 추출기
     */
    public ExcelColumn(String header, Function<T, Object> valueExtractor) {
        this(header, valueExtractor, null, ExcelCellStyle.none(), null);
    }

    /**
     * 스타일 포함 엑셀 컬럼 정의 생성자
     *
     * @param header 헤더명
     * @param valueExtractor 행 데이터 값 추출기
     * @param cellStyle 셀 스타일 정의
     */
    public ExcelColumn(String header, Function<T, Object> valueExtractor, ExcelCellStyle cellStyle) {
        this(header, valueExtractor, null, cellStyle, null);
    }

    /**
     * 스타일과 너비 포함 엑셀 컬럼 정의 생성자
     *
     * @param header 헤더명
     * @param valueExtractor 행 데이터 값 추출기
     * @param cellStyle 셀 스타일 정의
     * @param width 열 너비
     */
    public ExcelColumn(String header, Function<T, Object> valueExtractor, ExcelCellStyle cellStyle, Integer width) {
        this(header, valueExtractor, null, cellStyle, width);
    }

    /**
     * 행 데이터 값 추출
     *
     * @param item 행 데이터
     * @param rowNumber 행 번호
     * @return 행 데이터 값
     */
    public Object extractValue(T item, int rowNumber) {
        if (indexedValueExtractor != null) {
            return indexedValueExtractor.apply(item, rowNumber);
        }
        return valueExtractor.apply(item);
    }

    /**
     * 엑셀 컬럼 정의 생성
     *
     * @param header 헤더명
     * @param valueExtractor 행 데이터 값 추출기
     * @param <T> 행 데이터 타입
     * @return 엑셀 컬럼 정의
     */
    public static <T> ExcelColumn<T> of(String header, Function<T, Object> valueExtractor) {
        return new ExcelColumn<>(header, valueExtractor, ExcelCellStyle.none(), null);
    }

    /**
     * 너비 포함 엑셀 컬럼 정의 생성
     *
     * @param header 헤더명
     * @param valueExtractor 행 데이터 값 추출기
     * @param width 열 너비
     * @param <T> 행 데이터 타입
     * @return 엑셀 컬럼 정의
     */
    public static <T> ExcelColumn<T> of(String header, Function<T, Object> valueExtractor, int width) {
        return new ExcelColumn<>(header, valueExtractor, ExcelCellStyle.none(), width);
    }

    /**
     * 스타일 포함 엑셀 컬럼 정의 생성
     *
     * @param header 헤더명
     * @param valueExtractor 행 데이터 값 추출기
     * @param cellStyle 셀 스타일 정의
     * @param <T> 행 데이터 타입
     * @return 엑셀 컬럼 정의
     */
    public static <T> ExcelColumn<T> of(String header, Function<T, Object> valueExtractor, ExcelCellStyle cellStyle) {
        return new ExcelColumn<>(header, valueExtractor, cellStyle, null);
    }

    /**
     * 스타일과 너비 포함 엑셀 컬럼 정의 생성
     *
     * @param header 헤더명
     * @param valueExtractor 행 데이터 값 추출기
     * @param cellStyle 셀 스타일 정의
     * @param width 열 너비
     * @param <T> 행 데이터 타입
     * @return 엑셀 컬럼 정의
     */
    public static <T> ExcelColumn<T> of(
        String header,
        Function<T, Object> valueExtractor,
        ExcelCellStyle cellStyle,
        int width
    ) {
        return new ExcelColumn<>(header, valueExtractor, cellStyle, width);
    }

    /**
     * 자동 행 번호 엑셀 컬럼 정의 생성
     *
     * @param header 헤더명
     * @param <T> 행 데이터 타입
     * @return 엑셀 컬럼 정의
     */
    public static <T> ExcelColumn<T> rowNumber(String header) {
        return new ExcelColumn<>(header, null, (_, rowNumber) -> rowNumber, ExcelCellStyle.number(), null);
    }

    /**
     * 너비 포함 자동 행 번호 엑셀 컬럼 정의 생성
     *
     * @param header 헤더명
     * @param width 열 너비
     * @param <T> 행 데이터 타입
     * @return 엑셀 컬럼 정의
     */
    public static <T> ExcelColumn<T> rowNumber(String header, int width) {
        return new ExcelColumn<>(header, null, (_, rowNumber) -> rowNumber, ExcelCellStyle.number(), width);
    }

    /**
     * 콤마 포함 자동 행 번호 엑셀 컬럼 정의 생성
     *
     * @param header 헤더명
     * @param <T> 행 데이터 타입
     * @return 엑셀 컬럼 정의
     */
    public static <T> ExcelColumn<T> rowNumberComma(String header) {
        return new ExcelColumn<>(header, null, (_, rowNumber) -> rowNumber, ExcelCellStyle.numberComma(), null);
    }

    /**
     * 너비 포함 콤마 자동 행 번호 엑셀 컬럼 정의 생성
     *
     * @param header 헤더명
     * @param width 열 너비
     * @param <T> 행 데이터 타입
     * @return 엑셀 컬럼 정의
     */
    public static <T> ExcelColumn<T> rowNumberComma(String header, int width) {
        return new ExcelColumn<>(header, null, (_, rowNumber) -> rowNumber, ExcelCellStyle.numberComma(), width);
    }

    /**
     * 텍스트 포맷 엑셀 컬럼 정의 생성
     *
     * @param header 헤더명
     * @param valueExtractor 행 데이터 값 추출기
     * @param <T> 행 데이터 타입
     * @return 엑셀 컬럼 정의
     */
    public static <T> ExcelColumn<T> text(String header, Function<T, Object> valueExtractor) {
        return of(header, valueExtractor, ExcelCellStyle.text());
    }

    /**
     * 너비 포함 텍스트 포맷 엑셀 컬럼 정의 생성
     *
     * @param header 헤더명
     * @param valueExtractor 행 데이터 값 추출기
     * @param width 열 너비
     * @param <T> 행 데이터 타입
     * @return 엑셀 컬럼 정의
     */
    public static <T> ExcelColumn<T> text(String header, Function<T, Object> valueExtractor, int width) {
        return of(header, valueExtractor, ExcelCellStyle.text(), width);
    }

    /**
     * 숫자 포맷 엑셀 컬럼 정의 생성
     *
     * @param header 헤더명
     * @param valueExtractor 행 데이터 값 추출기
     * @param <T> 행 데이터 타입
     * @return 엑셀 컬럼 정의
     */
    public static <T> ExcelColumn<T> number(String header, Function<T, Object> valueExtractor) {
        return of(header, valueExtractor, ExcelCellStyle.number());
    }

    /**
     * 너비 포함 숫자 포맷 엑셀 컬럼 정의 생성
     *
     * @param header 헤더명
     * @param valueExtractor 행 데이터 값 추출기
     * @param width 열 너비
     * @param <T> 행 데이터 타입
     * @return 엑셀 컬럼 정의
     */
    public static <T> ExcelColumn<T> number(String header, Function<T, Object> valueExtractor, int width) {
        return of(header, valueExtractor, ExcelCellStyle.number(), width);
    }

    /**
     * 소수 숫자 포맷 엑셀 컬럼 정의 생성
     *
     * @param header 헤더명
     * @param valueExtractor 행 데이터 값 추출기
     * @param <T> 행 데이터 타입
     * @return 엑셀 컬럼 정의
     */
    public static <T> ExcelColumn<T> decimal(String header, Function<T, Object> valueExtractor) {
        return of(header, valueExtractor, ExcelCellStyle.decimal());
    }

    /**
     * 너비 포함 소수 숫자 포맷 엑셀 컬럼 정의 생성
     *
     * @param header 헤더명
     * @param valueExtractor 행 데이터 값 추출기
     * @param width 열 너비
     * @param <T> 행 데이터 타입
     * @return 엑셀 컬럼 정의
     */
    public static <T> ExcelColumn<T> decimal(String header, Function<T, Object> valueExtractor, int width) {
        return of(header, valueExtractor, ExcelCellStyle.decimal(), width);
    }

    /**
     * 콤마 포함 숫자 포맷 엑셀 컬럼 정의 생성
     *
     * @param header 헤더명
     * @param valueExtractor 행 데이터 값 추출기
     * @param <T> 행 데이터 타입
     * @return 엑셀 컬럼 정의
     */
    public static <T> ExcelColumn<T> numberComma(String header, Function<T, Object> valueExtractor) {
        return of(header, valueExtractor, ExcelCellStyle.numberComma());
    }

    /**
     * 너비 포함 콤마 숫자 포맷 엑셀 컬럼 정의 생성
     *
     * @param header 헤더명
     * @param valueExtractor 행 데이터 값 추출기
     * @param width 열 너비
     * @param <T> 행 데이터 타입
     * @return 엑셀 컬럼 정의
     */
    public static <T> ExcelColumn<T> numberComma(String header, Function<T, Object> valueExtractor, int width) {
        return of(header, valueExtractor, ExcelCellStyle.numberComma(), width);
    }

    /**
     * 콤마 포함 소수 숫자 포맷 엑셀 컬럼 정의 생성
     *
     * @param header 헤더명
     * @param valueExtractor 행 데이터 값 추출기
     * @param <T> 행 데이터 타입
     * @return 엑셀 컬럼 정의
     */
    public static <T> ExcelColumn<T> decimalComma(String header, Function<T, Object> valueExtractor) {
        return of(header, valueExtractor, ExcelCellStyle.decimalComma());
    }

    /**
     * 너비 포함 콤마 소수 숫자 포맷 엑셀 컬럼 정의 생성
     *
     * @param header 헤더명
     * @param valueExtractor 행 데이터 값 추출기
     * @param width 열 너비
     * @param <T> 행 데이터 타입
     * @return 엑셀 컬럼 정의
     */
    public static <T> ExcelColumn<T> decimalComma(String header, Function<T, Object> valueExtractor, int width) {
        return of(header, valueExtractor, ExcelCellStyle.decimalComma(), width);
    }
}
