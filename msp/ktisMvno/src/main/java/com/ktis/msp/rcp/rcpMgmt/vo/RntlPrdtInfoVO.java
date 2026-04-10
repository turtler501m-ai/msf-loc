package com.ktis.msp.rcp.rcpMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class RntlPrdtInfoVO extends BaseVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orgnId;
	private String strtYm;
	private String endYm;
	private String prdtId;
	private String prdtNm;
	private String prdtCode;
	private String buyAmnt;
	private String rentalCost;
	private String baseYm;
	
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
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
	public String getPrdtId() {
		return prdtId;
	}
	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}
	public String getPrdtNm() {
		return prdtNm;
	}
	public void setPrdtNm(String prdtNm) {
		this.prdtNm = prdtNm;
	}
	public String getPrdtCode() {
		return prdtCode;
	}
	public void setPrdtCode(String prdtCode) {
		this.prdtCode = prdtCode;
	}
	public String getBuyAmnt() {
		return buyAmnt;
	}
	public void setBuyAmnt(String buyAmnt) {
		this.buyAmnt = buyAmnt;
	}
	public String getRentalCost() {
		return rentalCost;
	}
	public void setRentalCost(String rentalCost) {
		this.rentalCost = rentalCost;
	}
	public String getBaseYm() {
		return baseYm;
	}
	public void setBaseYm(String baseYm) {
		this.baseYm = baseYm;
	}
	
}
