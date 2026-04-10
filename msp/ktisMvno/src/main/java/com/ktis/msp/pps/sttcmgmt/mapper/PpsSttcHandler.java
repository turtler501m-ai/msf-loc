package com.ktis.msp.pps.sttcmgmt.mapper;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import com.ktis.msp.base.dao.ExcelResultHandler;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.excel.ExcelWriter;

public class PpsSttcHandler extends ExcelResultHandler {
	
	public PpsSttcHandler(){
		
	}
	
	public PpsSttcHandler(ExcelWriter writer, Map<String, XSSFCellStyle> styles, ExcelParam param) {
		super(writer, styles, param);
	}
	
	@Override
	public HashMap<String, Object> refine(Object obj) {
		
		return (HashMap<String, Object>) obj;
	}

}
