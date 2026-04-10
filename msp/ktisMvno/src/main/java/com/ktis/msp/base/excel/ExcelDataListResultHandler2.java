package com.ktis.msp.base.excel;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktis.msp.base.EgovSampleExcepHndlr;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class ExcelDataListResultHandler2 implements ResultHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleExcepHndlr.class);

//	private int iTotRowCount = 0;
	private int rowNum = 0;
	/**
	 * @uml.property  name="sw"
	 * @uml.associationEnd  
	 */
	private final ExcelWriter sw;
	private final Map<String, XSSFCellStyle> styles;
//	private XSSFWorkbook wb;
	
	private final int[] intLen;
	private final String[] strValue;
	private final String[] strHead;
	
	
	public ExcelDataListResultHandler2(ExcelWriter sw, Map<String, XSSFCellStyle> styles
//			public ExcelDataListResultHandler(XSSFWorkbook wb, ExcelWriter sw, Map<String, CellStyle> styles
			, String[] strHead
			, String[] strValue
			, int[] intLen){
		this.sw = sw;
//		this.wb = wb;
		this.styles = styles;
		
		this.strHead = strHead;
		this.strValue = strValue;
		this.intLen = intLen;
		
//		createSheetTiles((XSSFCellStyle)this.styles.get("header"));
		createSheetTiles();
		createSum();
	}
	
	@Override
	public void handleResult(ResultContext context) {
		EgovMap egovMap = (EgovMap)context.getResultObject();
//		addRow(egovMap, (XSSFCellStyle)this.styles.get("data"));
		addRow(egovMap);
	}
	
//	private void createSheetTiles(XSSFCellStyle cellStyle){
	private void createSheetTiles(){
//        int i = 0;
        
        try {
            //타이틀
        	this.sw.insertRow(this.rowNum);
        	
        	int styleIndex = this.styles.get("header").getIndex();
			
			for (int j = 0; j < this.strHead.length; j++) {
				sw.createCell(j, this.strHead[j], styleIndex);
			}
            
            this.sw.endRow();
            
        } catch (Exception ex) {
//            throw new RuntimeException (ex);
			//20210714 소스코드점검수정
			LOGGER.error("Connection Exception occurred");
        }
//        this.iTotRowCount++;
        this.rowNum ++;
    }
	
	private void createSum() {
		try {
            //상단 합계
        	this.sw.insertRow(this.rowNum);
        	int styleIndex = this.styles.get("mid").getIndex();
        	
        	sw.createCell(0, "", styleIndex);
			sw.createCell(1, "", styleIndex);
			sw.createCell(2, "합계", this.styles.get("header").getIndex());
			String strVal = "";
			String arr[] = { "D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","AA","AB","AC","AD","AE","AF","AG","AH","AI","AJ","AK","AL","AM" };
			
		    for(int i=0; i<arr.length; i++) {
		    	strVal = "SUM(" + arr[i] + 3 + ":" + arr[i] + 1000000 + ")";
				sw.createCellFormula(i+3, strVal , styles.get("number").getIndex());
		    }
		    
            this.sw.endRow();
            
            
            
        } catch (Exception ex) {
//            throw new RuntimeException (ex);
			//20210714 소스코드점검수정
			LOGGER.error("Connection Exception occurred");
        }
//        this.iTotRowCount++;
        this.rowNum ++;
	}

	private void addRow(EgovMap map) {
//        int i = 0;
        try {
        	
        	//엑셀 데이터
            this.sw.insertRow(this.rowNum);
            for(int j=0; j < this.strValue.length; j++) {
            	String strVal = "";
            	
            	if(j==2) {
					//strVal = "100" ;
					int n = rowNum + 1;
					strVal = "SUM(D" + n + ":AM" + n + ")";
					sw.createCellFormula(j, strVal , styles.get("number").getIndex());
					continue;
				}
				if(intLen[j] == 1){
					strVal = map.get(strValue[j]) != null && !"".equals(String.valueOf(map.get(strValue[j]))) ? String.valueOf(map.get(strValue[j])) : "0";
					this.sw.createCell(j, Double.parseDouble(strVal), styles.get("number").getIndex());
				}else{
					strVal = map.get(strValue[j]) != null && !"".equals(String.valueOf(map.get(strValue[j]))) ? String.valueOf(map.get(strValue[j])) : "";
					this.sw.createCell(j, strVal, styles.get("mid").getIndex());
				}
            }
            this.sw.endRow();
            
        } catch (Exception ex) {
//            throw new RuntimeException (ex);
        	//20210714 소스코드점검수정
        	LOGGER.error("Connection Exception occurred");
        }
        
        // data count 증가.
//        this.iTotRowCount++;
        this.rowNum ++;
    }
	
    
}
