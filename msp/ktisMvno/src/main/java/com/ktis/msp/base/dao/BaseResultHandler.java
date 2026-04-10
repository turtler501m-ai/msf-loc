package com.ktis.msp.base.dao;

import java.util.Map;

import org.apache.ibatis.session.ResultHandler;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.excel.ExcelWriter;

public interface BaseResultHandler extends ResultHandler {

	public void setWriter(ExcelWriter writer);
	
	public void setStyle(Map<String, XSSFCellStyle> styles);
	
	public void setExcelParam(ExcelParam param);
	
}
