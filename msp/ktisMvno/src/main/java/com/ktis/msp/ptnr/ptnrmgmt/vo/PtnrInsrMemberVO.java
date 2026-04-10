package com.ktis.msp.ptnr.ptnrmgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class PtnrInsrMemberVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2330294945199583949L;
	
	private String parnterId;		//파트너 아이디
	
	private String searchBaseDate;
	private String searchInsrStatus;
	private String searchPymnYn;
	private String searchEfctYn;
	private String searchRateCd;
	private String searchCd;
	private String searchVal;
	private String searchInsrCd;
	
	private String contractNum;		// 계약번호
	private String insrCd;			// 보험코드
	
	public String getParnterId() {
		return parnterId;
	}
	public void setParnterId(String parnterId) {
		this.parnterId = parnterId;
	}
	public String getSearchBaseDate() {
		return searchBaseDate;
	}
	public void setSearchBaseDate(String searchBaseDate) {
		this.searchBaseDate = searchBaseDate;
	}
	public String getSearchInsrStatus() {
		return searchInsrStatus;
	}
	public void setSearchInsrStatus(String searchInsrStatus) {
		this.searchInsrStatus = searchInsrStatus;
	}
	public String getSearchPymnYn() {
		return searchPymnYn;
	}
	public void setSearchPymnYn(String searchPymnYn) {
		this.searchPymnYn = searchPymnYn;
	}
	public String getSearchEfctYn() {
		return searchEfctYn;
	}
	public void setSearchEfctYn(String searchEfctYn) {
		this.searchEfctYn = searchEfctYn;
	}
	public String getSearchRateCd() {
		return searchRateCd;
	}
	public void setSearchRateCd(String searchRateCd) {
		this.searchRateCd = searchRateCd;
	}
	public String getSearchCd() {
		return searchCd;
	}
	public void setSearchCd(String searchCd) {
		this.searchCd = searchCd;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	public String getSearchInsrCd() {
		return searchInsrCd;
	}
	public void setSearchInsrCd(String searchInsrCd) {
		this.searchInsrCd = searchInsrCd;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getInsrCd() {
		return insrCd;
	}
	public void setInsrCd(String insrCd) {
		this.insrCd = insrCd;
	}
	
	
}
