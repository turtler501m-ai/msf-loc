package com.ktmmobile.mcp.payinfo.dto;

import java.io.Serializable;

public class PayInfoFormDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String appKey;			//키
	public String colunmName;		//컬럼명
	public String metaRow;			//좌표값 가로
	public String metaLine;			//좌표값 세로
	public String codedataYn;		//코드데이터 유무
	public String codedata;			//코드데이터
	/**
	 * @return the appKey
	 */
	public String getAppKey() {
		return appKey;
	}
	/**
	 * @param appKey the appKey to set
	 */
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	/**
	 * @return the columnName
	 */
	public String getColunmName() {
		return colunmName;
	}
	/**
	 * @param colunmName the colunmName to set
	 */
	public void setColunmName(String colunmName) {
		this.colunmName = colunmName;
	}
	/**
	 * @return the metaRow
	 */
	public String getMetaRow() {
		return metaRow;
	}
	/**
	 * @param metaRow the metaRow to set
	 */
	public void setMetaRow(String metaRow) {
		this.metaRow = metaRow;
	}
	/**
	 * @return the metaLine
	 */
	public String getMetaLine() {
		return metaLine;
	}
	/**
	 * @param metaLine the metaLine to set
	 */
	public void setMetaLine(String metaLine) {
		this.metaLine = metaLine;
	}
	/**
	 * @return the codedataYn
	 */
	public String getCodedataYn() {
		return codedataYn;
	}
	/**
	 * @param codedataYn the codedataYn to set
	 */
	public void setCodedataYn(String codedataYn) {
		this.codedataYn = codedataYn;
	}
	/**
	 * @return the codedata
	 */
	public String getCodedata() {
		return codedata;
	}
	/**
	 * @param codedata the codedata to set
	 */
	public void setCodedata(String codedata) {
		this.codedata = codedata;
	}
	
	
	
}
