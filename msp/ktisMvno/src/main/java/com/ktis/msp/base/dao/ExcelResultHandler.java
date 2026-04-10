package com.ktis.msp.base.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktis.msp.base.EgovSampleExcepHndlr;
import com.ktis.msp.base.excel.ExcelParam;
import com.ktis.msp.base.excel.ExcelWriter;

public abstract class ExcelResultHandler extends BaseResultHandlerImpl{
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleExcepHndlr.class);
	
	private int rowNum = 0;
	
	public ExcelResultHandler(){
		
	}
	
	public ExcelResultHandler(ExcelWriter writer, Map<String, XSSFCellStyle> styles, ExcelParam param){
		this.writer = writer;
		this.styles = styles;
		this.param 	= param;
		addHead();
	}
		
	public void init(ExcelWriter writer, Map<String, XSSFCellStyle> styles, ExcelParam param){	
		this.writer = writer;
		this.styles = styles;
		this.param 	= param;
		addHead();
	}

	@Override
	public void handleResult(ResultContext context) {
		
		if(context.getResultObject() != null) {
			HashMap<String, Object> r = refine(context.getResultObject());
			addBody(r);
		}
		
	}
	
	public abstract HashMap<String, Object> refine(Object obj);
	
	private void addHead() {
	    try {
	    	
	    	this.writer.insertRow(this.rowNum);
	    	
	    	for (int i = 0; i < param.getStrHeadCount(); i++) {
	        	this.writer.createCell(i, param.getStrHead(i), styles.get("head").getIndex());
			}
	    	this.writer.endRow();
	        
	    } catch (Exception ex) {
			//20210714 소스코드점검수정
			LOGGER.error("Connection Exception occurred");
	    }
	    this.rowNum ++;
	}

	private void addBody(HashMap<String, Object> row) {
		try {
	        this.writer.insertRow(this.rowNum);
	    	for (int i = 0; i < param.getStrValueCount(); i++) {
	    		
	    		Object d = row.get(param.getStrValue(i));
	    		
	    		String data = (d==null)?"":d.toString();
	    		
	    		if(param.getIntLen(i) == 1) { // Numeric
	    			if(data.equals("")) {
	    				this.writer.createCell(i, 0, styles.get("bodyNumeric").getIndex());
	    			}else{
	    				this.writer.createCell(i, Double.parseDouble(data), styles.get("bodyNumeric").getIndex());
	    			}
	    			
	    		}else{
	    			this.writer.createCell(i, data, styles.get("body").getIndex());
	    		}
	    		
	    	}
	    	this.writer.endRow();
	    } catch (Exception ex) {
	    	//20210714 소스코드점검수정
			LOGGER.error("Connection Exception occurred");
	    }
	    this.rowNum ++;
	}



	
	
}
