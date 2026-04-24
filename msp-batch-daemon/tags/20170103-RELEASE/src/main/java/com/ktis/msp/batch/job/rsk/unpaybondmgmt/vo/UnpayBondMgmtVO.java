package com.ktis.msp.batch.job.rsk.unpaybondmgmt.vo;


public class UnpayBondMgmtVO {
	
	private String stdrYm; //기준월
	private String strtYm; //청구월 시작
	private String endYm;  //청구월 종료
	
	private String searchType;
	private String searchVal;
	private String unpdYn;
	
	private String userId;
	
	public String getStdrYm() {
		return stdrYm;
	}
	public void setStdrYm(String stdrYm) {
		this.stdrYm = stdrYm;
	}
	public String getStrtYm() {
		return strtYm;
	}
	public void setStrtYm(String strtYm) {
		this.strtYm = strtYm;
	}
	public String getEndYm() {
		return endYm;
	}
	public void setEndYm(String endYm) {
		this.endYm = endYm;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	public String getUnpdYn() {
		return unpdYn;
	}
	public void setUnpdYn(String unpdYn) {
		this.unpdYn = unpdYn;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
