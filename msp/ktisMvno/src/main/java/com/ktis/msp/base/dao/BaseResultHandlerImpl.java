package com.ktis.msp.base.dao;

import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.excel.ExcelWriter;

public abstract class BaseResultHandlerImpl implements BaseResultHandler {
	
	protected ExcelParam param;
	protected ExcelWriter writer;
	protected Map<String, XSSFCellStyle> styles;
	
	
	public BaseResultHandlerImpl(){
		super();
	}

	@Override
	public void setWriter(ExcelWriter writer) {
		this.writer = writer;
	}

	@Override
	public void setStyle(Map<String, XSSFCellStyle> styles) {
		this.styles = styles;
	}

	@Override
	public void setExcelParam(ExcelParam param) {
		this.param = param;
	}
	
	
	

}
