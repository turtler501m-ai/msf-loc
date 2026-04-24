package com.ktis.msp.batch.manager.common.excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.poi.ss.usermodel.CellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.manager.common.ApplicationContextProvider;
import com.ktis.msp.batch.manager.common.masking.MaskingService;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class ExcelDataListResultHandler4 implements ResultHandler {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
//	private int iTotRowCount = 0;
	private int rowNum = 0;
	/**
	 * @uml.property  name="sw"
	 * @uml.associationEnd  
	 */
	private final ExcelWriter sw;
	private final Map<String, CellStyle> styles;
	
	private final int[] intLen;
	private final String[] strValue;
	private final String[] strHead;
	
	private final HashMap<String, String> maskFields;
	private List<?> lCmnMskGrp;
	
	private final MaskingService maskingService = (MaskingService) ApplicationContextProvider.getApplicationContext().getBean("maskingService");
	
	public ExcelDataListResultHandler4(ExcelWriter sw
									, Map<String, CellStyle> styles
									, String[] strHead
									, String[] strValue
									, int[] intLen
									, HashMap<String, String> maskFields
									, Map<String, Object> paramMap) throws MvnoServiceException {
		this.sw = sw;
		this.styles = styles;
		
		this.strHead = strHead;
		this.strValue = strValue;
		this.intLen = intLen;
		
		this.maskFields = maskFields;
		
		this.lCmnMskGrp = maskingService.beforeSetMask(paramMap);
		
		createSheetTiles();
	}
	
	public void handleResult(ResultContext context) {
		EgovMap egovMap = (EgovMap)context.getResultObject();
		
		try {
			addRow(egovMap);
		} catch (MvnoServiceException e) {
			LOGGER.debug(e.getMessage() + " ==> " + e.getCause());
		}
	}
	
	private void createSheetTiles() throws MvnoServiceException {
		
		try {
			// 타이틀
			this.sw.insertRow(this.rowNum);
			
			int styleIndex = this.styles.get("header").getIndex();
			
			for (int j = 0; j < this.strHead.length; j++) {
				sw.createCell(j, this.strHead[j], styleIndex);
			}
			
			this.sw.endRow();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MvnoServiceException("ECMN1010", ex);
		}
		this.rowNum++;
	}
	
	private void addRow(EgovMap map) throws MvnoServiceException {
		
		try {
			if(lCmnMskGrp != null) {
				maskingService.setMask(map, maskFields, lCmnMskGrp);
			}
			
			// 엑셀 데이터
			this.sw.insertRow(this.rowNum);
			for (int j = 0; j < this.strValue.length; j++) {
				String strVal = "";
				
				if (intLen[j] == 1) {
					strVal = map.get(strValue[j]) != null && !"".equals(String.valueOf(map.get(strValue[j]))) 
								? String .valueOf(map.get(strValue[j])) : "0";
					this.sw.createCell(j, Double.parseDouble(strVal), styles.get("number").getIndex());
				} else if (intLen[j] == 2) {
					strVal = map.get(strValue[j]) != null && !"".equals((String) map.get(strValue[j])) 
								? map.get(strValue[j]).toString() : "";
					this.sw.createCell(j, strVal, styles.get("left").getIndex());
				} else {
					strVal = map.get(strValue[j]) != null && !"".equals(String.valueOf(map.get(strValue[j]))) 
								? String.valueOf(map.get(strValue[j])) : "";
					this.sw.createCell(j, strVal, styles.get("mid").getIndex());
				}
			}
			this.sw.endRow();

		} catch (Exception ex) {
			// throw new RuntimeException (ex);
			//ex.printStackTrace();
			throw new MvnoServiceException("ECMN1011", ex);
		}

		// data count 증가.
		// this.iTotRowCount++;
		this.rowNum++;
	}
	
}
