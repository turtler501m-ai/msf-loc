package com.ktis.msp.batch.manager.common.excel;

import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.DateUtil;

public class ExcelWriter {

	private final Writer _out;

	private int rownum;

	public ExcelWriter(Writer out) {
		_out = out;
	}

	public void beginSheet() throws IOException {
		_out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">");
		_out.write("<sheetData>\n");
	}

	public void endSheet() throws IOException {
		_out.write("</sheetData>");
		_out.write("</worksheet>");
	}

	public void beginWorkSheet() throws IOException {
		_out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">");
	}

	public void endWorkSheet() throws IOException {
		_out.write("</worksheet>");
	}

	public void beginSheetBond() throws IOException {
		_out.write("<sheetData>\n");
	}

	public void endSheetBond() throws IOException {
		_out.write("</sheetData>");
	}

	/**
	 * Row 추가
	 * 
	 * @param rownum
	 *            0-based row number
	 */
	public void insertRow(int rownum) throws IOException {
		_out.write("<row r=\"" + (rownum + 1) + "\">\n");
		this.rownum = rownum;
	}

	/**
	 * Insert row end marker
	 */
	public void endRow() throws IOException {
		_out.write("</row>\n");
	}

	public void createCell(int columnIndex, String value, int styleIndex)
			throws IOException {
		String ref = new CellReference(rownum, columnIndex).formatAsString();
		String refNew = ref.replaceAll("\\$", "");
		// if(value == null)
		// value = "";

		_out.write("<c r=\"" + refNew + "\" t=\"inlineStr\"");
		if (styleIndex != -1)
			_out.write(" s=\"" + styleIndex + "\"");
		_out.write(">");
		_out.write("<is><t><![CDATA[" + value + "]]></t></is>");
		_out.write("</c>");
	}

	public void createCellFormula(int columnIndex, String value, int styleIndex)
			throws IOException {
		String ref = new CellReference(rownum, columnIndex).formatAsString();
		ref = ref.replaceAll("\\$", "");
		// if(value == null)
		// value = "";

		_out.write("<c r=\"" + ref + "\" t=\"Str\"");
		if (styleIndex != -1)
			_out.write(" s=\"" + styleIndex + "\"");
		_out.write(">");
		_out.write("<f><![CDATA[" + value + "]]></f>");
		_out.write("</c>");
	}

	public void createCell(int columnIndex, String value) throws IOException {
		createCell(columnIndex, value, -1);
	}

	public void createCell(int columnIndex, double value, int styleIndex)
			throws IOException {
		String ref = new CellReference(rownum, columnIndex).formatAsString();
		ref = ref.replaceAll("\\$", "");
		_out.write("<c r=\"" + ref + "\" t=\"n\"");
		if (styleIndex != -1)
			_out.write(" s=\"" + styleIndex + "\"");
		_out.write(">");
		_out.write("<v><![CDATA[" + value + "]]></v>");
		_out.write("</c>");

	}

	public void createCell(int columnIndex, double value) throws IOException {
		createCell(columnIndex, value, -1);
	}

	public void createCell(int columnIndex, Calendar value, int styleIndex)
			throws IOException {
		createCell(columnIndex, DateUtil.getExcelDate(value, false), styleIndex);
	}

	public void customCell(int min, int max, int width, int customWidth)
			throws IOException {
		_out.write("<cols>");
		_out.write("<col min=\"" + min + "\" max=\"" + max + "\" width=\""
				+ width + "\" customWidth=\"" + customWidth + "\" />");
		_out.write("</cols>");
	}

	public void customCell(List<SfExcelAttribute> customCellSizeList)
			throws IOException {
		_out.write("<cols>");
		for (SfExcelAttribute sfExcelAttribute : customCellSizeList) {
			_out.write("<col min=\"" + sfExcelAttribute.getMin() + "\" max=\""
					+ sfExcelAttribute.getMax() + "\" width=\""
					+ sfExcelAttribute.getWidth() + "\" customWidth=\""
					+ sfExcelAttribute.getCustomWidth() + "\" />");
		}
		_out.write("</cols>");
	}

	/**
	 * 셀 합치기
	 * 
	 * @param mergeCell
	 *            행으로만 테스트 ... [A1 : F5]
	 * @throws IOException
	 */
	public void mergeCell(String[] mergeCell) throws IOException {
		_out.write("<mergeCells>");

		for (int i = 0; i < mergeCell.length; i++) {
			_out.write("<mergeCell ref=\"");
			_out.write(mergeCell[i]);
			_out.write("\" />");
		}
		_out.write("</mergeCells>");

	}

}
