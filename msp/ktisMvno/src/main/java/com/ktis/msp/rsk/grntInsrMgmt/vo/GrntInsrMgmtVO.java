package com.ktis.msp.rsk.grntInsrMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class GrntInsrMgmtVO extends BaseVo{

	private static final long serialVersionUID = 1L;
	private String workYm;
	private String searchType;
	private String searchVal;
	private String strtYm;
	private String endYm;
	
	public String getWorkYm() {
		return workYm;
	}
	public void setWorkYm(String workYm) {
		this.workYm = workYm;
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
}
