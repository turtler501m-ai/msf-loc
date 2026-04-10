package com.ktis.msp.base.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.excel.ExcelWriter;

public class MvnoExcelResultHandler extends ExcelResultHandler {

	public MvnoExcelResultHandler(ExcelWriter writer, Map<String, XSSFCellStyle> styles, ExcelParam param) {
		super(writer, styles, param);
	}

	@Override
	//@Crypto(decryptName="DBMSDec", fields = {"userSsn", "adSsn","cmsUserSsn","customerSsn"})
	public HashMap<String, Object> refine(Object obj) {
		return (HashMap<String, Object>) obj;
	}

}
