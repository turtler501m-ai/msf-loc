package com.ktis.msp.rsk.statMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class StatMgmt3gSubVO extends BaseVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// 조회조건
	private String stdrDt;
	private String subStatus;
	private String searchCd;
	private String searchVal;
	private String lstModelTp;
	private String lstRateTp;
	private String lstUsimTp;
	private String volteYn;
	
	public String getStdrDt() {
		return stdrDt;
	}
	public void setStdrDt(String stdrDt) {
		this.stdrDt = stdrDt;
	}
	
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
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
	public String getLstModelTp() {
		return lstModelTp;
	}
	public void setLstModelTp(String lstModelTp) {
		this.lstModelTp = lstModelTp;
	}
	public String getLstRateTp() {
		return lstRateTp;
	}
	public void setLstRateTp(String lstRateTp) {
		this.lstRateTp = lstRateTp;
	}
	public String getLstUsimTp() {
		return lstUsimTp;
	}
	public void setLstUsimTp(String lstUsimTp) {
		this.lstUsimTp = lstUsimTp;
	}
	public String getVolteYn() {
		return volteYn;
	}
	public void setVolteYn(String volteYn) {
		this.volteYn = volteYn;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
