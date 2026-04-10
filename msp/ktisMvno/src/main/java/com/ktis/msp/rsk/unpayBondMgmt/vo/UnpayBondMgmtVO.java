package com.ktis.msp.rsk.unpayBondMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class UnpayBondMgmtVO extends BaseVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String stdrYm; //기준월
	private String strtYm; //청구월 시작
	private String endYm;  //청구월 종료
	
	private String billYm;
	private String billItemCd;
	private String svcCntrNo;
	
	private String searchType;
	private String searchVal;
	private String unpdYn;
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
	public String getBillYm() {
		return billYm;
	}
	public void setBillYm(String billYm) {
		this.billYm = billYm;
	}
	public String getBillItemCd() {
		return billItemCd;
	}
	public void setBillItemCd(String billItemCd) {
		this.billItemCd = billItemCd;
	}
	public String getSvcCntrNo() {
		return svcCntrNo;
	}
	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
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
}
