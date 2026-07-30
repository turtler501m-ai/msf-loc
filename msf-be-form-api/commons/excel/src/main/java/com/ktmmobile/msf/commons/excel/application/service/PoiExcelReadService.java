package com.ktmmobile.msf.commons.excel.application.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.ktmmobile.msf.commons.excel.application.port.in.ExcelRowConsumer;
import com.ktmmobile.msf.commons.excel.application.port.in.ExcelReader;
import com.ktmmobile.msf.commons.excel.domain.vo.ExcelRow;
import com.ktmmobile.msf.commons.excel.domain.vo.ExcelReadOption;
import com.ktmmobile.msf.commons.excel.domain.vo.ExcelRowMapper;
import com.ktmmobile.msf.commons.excel.domain.vo.ExcelStreamingRowMapper;

/**
 * POI 엑셀 파일 읽기 서비스
 */
@Service
public class PoiExcelReadService implements ExcelReader {

    /**
     * 첫 번째 시트의 헤더 다음 행부터 엑셀 파일 읽기
     *
     * @param inputStream 엑셀 바이너리 입력 스트림
     * @param rowMapper 행 데이터 변환기
     * @param <T> 행 데이터 타입
     * @return 행 데이터 목록
     */
    @Override
    public <T> List<T> read(InputStream inputStream, ExcelRowMapper<T> rowMapper) throws IOException {
        return read(inputStream, ExcelReadOption.defaultOption(), rowMapper);
    }

    /**
     * 지정 시트 및 시작 행 기준 엑셀 파일 읽기
     *
     * @param inputStream 엑셀 바이너리 입력 스트림
     * @param option 엑셀 읽기 옵션
     * @param rowMapper 행 데이터 변환기
     * @param <T> 행 데이터 타입
     * @return 행 데이터 목록
     */
    @Override
    public <T> List<T> read(InputStream inputStream, ExcelReadOption option, ExcelRowMapper<T> rowMapper)
        throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(option.sheetIndex());
            List<T> rows = new ArrayList<>();
            for (int rowIndex = option.startRowIndex(); rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                rows.add(rowMapper.map(row, rowIndex));
            }
            return rows;
        }
    }

    /**
     * 첫 번째 시트의 헤더 다음 행부터 엑셀 파일 스트리밍 읽기
     *
     * @param inputStream 엑셀 바이너리 입력 스트림
     * @param rowMapper 행 데이터 변환기
     * @param rowConsumer 행 데이터 소비자
     * @param <T> 행 데이터 타입
     */
    @Override
    public <T> void stream(InputStream inputStream, ExcelStreamingRowMapper<T> rowMapper, ExcelRowConsumer<T> rowConsumer)
        throws IOException {
        stream(inputStream, ExcelReadOption.defaultOption(), rowMapper, rowConsumer);
    }

    /**
     * 지정 시트 및 시작 행 기준 엑셀 파일 스트리밍 읽기
     *
     * @param inputStream 엑셀 바이너리 입력 스트림
     * @param option 엑셀 읽기 옵션
     * @param rowMapper 행 데이터 변환기
     * @param rowConsumer 행 데이터 소비자
     * @param <T> 행 데이터 타입
     */
    @Override
    public <T> void stream(
        InputStream inputStream,
        ExcelReadOption option,
        ExcelStreamingRowMapper<T> rowMapper,
        ExcelRowConsumer<T> rowConsumer
    ) throws IOException {
        try (OPCPackage opcPackage = OPCPackage.open(inputStream)) {
            XSSFReader reader = new XSSFReader(opcPackage);
            StylesTable stylesTable = reader.getStylesTable();
            ReadOnlySharedStringsTable sharedStringsTable = new ReadOnlySharedStringsTable(opcPackage);
            XMLReader parser = XMLHelper.newXMLReader();
            DataFormatter dataFormatter = new DataFormatter();

            try (InputStream sheetStream = getSheetStream(reader, option.sheetIndex())) {
                ContentHandler handler = new XSSFSheetXMLHandler(
                    stylesTable,
                    null,
                    sharedStringsTable,
                    new StreamingSheetContentsHandler<>(option, rowMapper, rowConsumer),
                    dataFormatter,
                    false
                );
                parser.setContentHandler(handler);
                parser.parse(new InputSource(sheetStream));
            }
        } catch (OpenXML4JException | SAXException | ParserConfigurationException e) {
            throw new IOException("엑셀 파일을 스트리밍 방식으로 읽을 수 없습니다.", e);
        }
    }

    /**
     * 시트 입력 스트림 조회
     *
     * @param reader 엑셀 리더
     * @param sheetIndex 시트 인덱스
     * @return 시트 입력 스트림
     */
    private static InputStream getSheetStream(XSSFReader reader, int sheetIndex) throws IOException, OpenXML4JException {
        XSSFReader.SheetIterator sheetIterator = (XSSFReader.SheetIterator) reader.getSheetsData();
        int currentIndex = 0;
        while (sheetIterator.hasNext()) {
            InputStream sheetStream = sheetIterator.next();
            if (currentIndex == sheetIndex) {
                return sheetStream;
            }
            sheetStream.close();
            currentIndex++;
        }
        throw new IllegalArgumentException("엑셀 시트를 찾을 수 없습니다. sheetIndex: " + sheetIndex);
    }

    /**
     * 스트리밍 시트 행 처리기
     *
     * @param <T> 행 데이터 타입
     */
    private static class StreamingSheetContentsHandler<T> implements XSSFSheetXMLHandler.SheetContentsHandler {

        private final ExcelReadOption option;
        private final ExcelStreamingRowMapper<T> rowMapper;
        private final ExcelRowConsumer<T> rowConsumer;
        private Map<Integer, String> values = new HashMap<>();

        private StreamingSheetContentsHandler(
            ExcelReadOption option,
            ExcelStreamingRowMapper<T> rowMapper,
            ExcelRowConsumer<T> rowConsumer
        ) {
            this.option = option;
            this.rowMapper = rowMapper;
            this.rowConsumer = rowConsumer;
        }

        @Override
        public void startRow(int rowNum) {
            values = new HashMap<>();
        }

        @Override
        public void endRow(int rowNum) {
            if (rowNum < option.startRowIndex()) {
                return;
            }
            T row = rowMapper.map(new ExcelRow(rowNum, Map.copyOf(values)));
            rowConsumer.accept(row);
        }

        @Override
        public void cell(String cellReference, String formattedValue, org.apache.poi.xssf.usermodel.XSSFComment comment) {
            int columnIndex = new org.apache.poi.ss.util.CellReference(cellReference).getCol();
            values.put(columnIndex, formattedValue == null ? "" : formattedValue.trim());
        }
    }
}
