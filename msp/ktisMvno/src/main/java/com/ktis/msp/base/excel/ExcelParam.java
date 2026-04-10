package com.ktis.msp.base.excel;

import java.util.List;

public class ExcelParam {
	
	String fileName;
	
	String sheetName;
	
	String[] strHead;
	
	int[] intWidth;
	
	String[] strValue;
	
	int[] intLen;
	
	String excelPath;
	
	List<?> cmnMaskGrpList;
	
	
	

	public String getExcelPath() {
		return excelPath;
	}

	public void setExcelPath(String excelPath) {
		this.excelPath = excelPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public int[] getIntLen() {
		return intLen;
	}

	public void setIntLen(int ... intLen) {
		this.intLen = intLen;
	}
	
	public int getIntLen(int index) {
		return intLen[index];
	}
	
	public int getIntLenCount(){
		return getIntLen().length;
	}
	

	public String[] getStrHead() {
		return strHead;
	}
	
	public String getStrHead(int index) {
		return strHead[index];
	}
	
	public int getStrHeadCount(){
		return getStrHead().length;
	}

	public void setStrHead(String ... strHead) {
		this.strHead = strHead;
	}

	public int[] getIntWidth() {
		return intWidth;
	}

	public void setIntWidth(int ... intWidth) {
		this.intWidth = intWidth;
	}

	public String[] getStrValue() {
		return strValue;
	}

	public String getStrValue(int index) {
		return strValue[index];
	}
	
	public int getStrValueCount(){
		return getStrValue().length;
	}
	
	public void setStrValue(String ... strValue) {
		this.strValue = strValue;
	}

	public List<?> getCmnMaskGrpList() {
		return cmnMaskGrpList;
	}

	public void setCmnMaskGrpList(List<?> cmnMaskGrpList) {
		this.cmnMaskGrpList = cmnMaskGrpList;
	}
	
	
	

}
