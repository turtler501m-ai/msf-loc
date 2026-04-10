package com.ktis.msp.rntl.rntlMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class RntlMgmtByPurchStandVO extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orgnId;
	private String agncyNm;
	private String strtYm;
	private String endYm;
	private String prdtId;
	private String prdtCode;
	private String deplrictCost;
	private String buyAmnt;
	private String rentalCost;
	private String virfyCost;
	private String grntAmnt;
	private String regId;
	private String regDttm;
	private String rvisnId;
	private String rvisnDttm;
	private String baseDt;
	
	/* 페이징 처리용 */
	public int TOTAL_COUNT;
	public String ROW_NUM;
	public String LINENUM;
	
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	public String getAgncyNm() {
		return agncyNm;
	}
	public void setAgncyNm(String agncyNm) {
		this.agncyNm = agncyNm;
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
	public String getPrdtCode() {
		return prdtCode;
	}
	public void setPrdtCode(String prdtCode) {
		this.prdtCode = prdtCode;
	}
	public String getDeplrictCost() {
		return deplrictCost;
	}
	public void setDeplrictCost(String deplrictCost) {
		this.deplrictCost = deplrictCost;
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
	public String getVirfyCost() {
		return virfyCost;
	}
	public void setVirfyCost(String virfyCost) {
		this.virfyCost = virfyCost;
	}
	public String getGrntAmnt() {
		return grntAmnt;
	}
	public void setGrntAmnt(String grntAmnt) {
		this.grntAmnt = grntAmnt;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegDttm() {
		return regDttm;
	}
	public void setRegDttm(String regDttm) {
		this.regDttm = regDttm;
	}
	public String getRvisnId() {
		return rvisnId;
	}
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}
	public String getRvisnDttm() {
		return rvisnDttm;
	}
	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}
	public String getBaseDt() {
		return baseDt;
	}
	public void setBaseDt(String baseDt) {
		this.baseDt = baseDt;
	}
	public int getTOTAL_COUNT() {
		return TOTAL_COUNT;
	}
	public void setTOTAL_COUNT(int tOTAL_COUNT) {
		TOTAL_COUNT = tOTAL_COUNT;
	}
	public String getROW_NUM() {
		return ROW_NUM;
	}
	public void setROW_NUM(String rOW_NUM) {
		ROW_NUM = rOW_NUM;
	}
	public String getLINENUM() {
		return LINENUM;
	}
	public void setLINENUM(String lINENUM) {
		LINENUM = lINENUM;
	}
}
