package com.ktis.msp.base.excel;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class ExcelDataListResultHandler3 implements ResultHandler {

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
	
	//v2018.11 PMD 적용 소스 수정
	//private Logger logger = LogManager.getLogger(getClass());
	public Logger logger = LogManager.getLogger(getClass());
	
	public ExcelDataListResultHandler3(ExcelWriter sw, Map<String, XSSFCellStyle> styles
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
		//v2018.11 PMD 적용 소스 수정
	   //int i = 0;
		
		try {
			//타이틀
			this.sw.insertRow(this.rowNum);
			
			int styleIndex = this.styles.get("header").getIndex();
			
			for (int j = 0; j < this.strHead.length; j++) {
				sw.createCell(j, this.strHead[j], styleIndex);
			}
			
			this.sw.endRow();
			
		} catch (Exception ex) {
			//20210714 소스코드점검수정
			logger.error("Connection Exception occurred");
		}
		
//		this.iTotRowCount++;
		this.rowNum ++;
	}
	
	private void createSum() {
		try {
			//상단 합계
			this.sw.insertRow(this.rowNum);
			int styleIndex = this.styles.get("mid").getIndex();
			sw.createCell(0, "", styleIndex);
			sw.createCell(1, "", styleIndex);
			sw.createCell(2, "", styleIndex);
			sw.createCell(3, "", styleIndex);
			sw.createCell(4, "", styleIndex);
			sw.createCell(5, "", styleIndex);
			sw.createCell(6, "", styleIndex);
			sw.createCellFormula(7, "SUM(H3:H1000000)", styles.get("number").getIndex());
			sw.createCellFormula(8, "SUM(I3:I1000000)", styles.get("number").getIndex());
			sw.createCellFormula(9, "SUM(J3:J1000000)", styles.get("number").getIndex());
			sw.createCell(10, "", styleIndex);
			sw.createCell(11, "합계", styleIndex);
			
			String strVal = "";
			String arr[] = { "M" ,"N" ,"O" ,"P", "Q", "R", "S", "T", "U", "V", "W" ,"X" ,"Y" ,"Z" //14
							,"AA","AB","AC","AD","AE","AF","AG","AH","AI","AJ","AK","AL","AM","AN","AO","AP","AQ","AR","AS","AT","AU","AV","AW","AX","AY","AZ" //26
							,"BA","BB","BC","BD","BE","BF","BG","BH","BI","BJ","BK","BL","BM","BN","BO","BP","BQ","BR","BS","BT","BU","BV","BW","BX","BY","BZ" //26
							,"CA","CB","CC","CD","CE","CF","CG","CH","CI","CJ","CK","CL","CM","CN","CO","CP","CQ","CR","CS","CT","CU","CV","CW","CX","CY","CZ" //26
							,"DA","DB","DC","DD","DE","DF","DG","DH","DI","DJ","DK","DL","DM","DN","DO","DP","DQ","DR","DS","DT","DU","DV","DW","DX","DY","DZ" //26
							,"EA","EB" // 2
						};
			// 총 120개 컬럼 12 * 5 * 2 ( 청구/수납 )
			for(int i=0; i<arr.length; i++) {
				strVal = "SUM(" + arr[i] + 3 + ":" + arr[i] + 1000000 + ")";
				sw.createCellFormula(i+12, strVal , styles.get("number").getIndex());
			}
			
			this.sw.endRow();
			
		} catch (Exception ex) {
			//20210714 소스코드점검수정
			logger.error("Connection Exception occurred");
		}
//		this.iTotRowCount++;
		this.rowNum ++;
	}

	private void addRow(EgovMap map) {
		
		logger.info("data = " + map.toString());
		
		try {
			
			//엑셀 데이터
			this.sw.insertRow(this.rowNum);
			
			for(int i=0; i < this.strValue.length; i++) {
				
				String strVal = "";
				
				if(intLen[i] == 1){
					strVal = map.get(strValue[i]) != null && !"".equals(String.valueOf(map.get(strValue[i]))) ? String.valueOf(map.get(strValue[i])) : "0";
					this.sw.createCell(i, Double.parseDouble(strVal), styles.get("number").getIndex());
				}else{
					strVal = map.get(strValue[i]) != null && !"".equals(String.valueOf(map.get(strValue[i]))) ? String.valueOf(map.get(strValue[i])) : "";
					this.sw.createCell(i, strVal, styles.get("mid").getIndex());
				}
			}
			
			this.sw.endRow();
			
		} catch (Exception ex) {
			//20210714 소스코드점검수정
			logger.error("Connection Exception occurred");
		}
		
		// data count 증가.
//		this.iTotRowCount++;
		this.rowNum ++;
	}
	
}
