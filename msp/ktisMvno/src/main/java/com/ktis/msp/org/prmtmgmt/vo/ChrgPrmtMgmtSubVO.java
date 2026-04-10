package com.ktis.msp.org.prmtmgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class ChrgPrmtMgmtSubVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9045258754875949571L;
	
	private String prmtId;
	private String rowCheck;
	private String orgnId;
	private String orgnNm;
	private String typeNm;
	private String orgnType;
	private String rateCd;
	private String rateNm;
	private String dataType;
	private String vasCd;
	private String vasNm;
	private String dupYn;
	private String baseAmt;
	private String onOffType;
	private String onOffTypeNm;
	private String statNm;				//조직상태명
	
	public String getPrmtId() {
		return prmtId;
	}
	public void setPrmtId(String prmtId) {
		this.prmtId = prmtId;
	}
	public String getRowCheck() {
		return rowCheck;
	}
	public void setRowCheck(String rowCheck) {
		this.rowCheck = rowCheck;
	}
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	public String getOrgnNm() {
		return orgnNm;
	}
	public void setOrgnNm(String orgnNm) {
		this.orgnNm = orgnNm;
	}
	public String getTypeNm() {
		return typeNm;
	}
	public void setTypeNm(String typeNm) {
		this.typeNm = typeNm;
	}
	public String getOrgnType() {
		return orgnType;
	}
	public void setOrgnType(String orgnType) {
		this.orgnType = orgnType;
	}
	public String getRateCd() {
		return rateCd;
	}
	public void setRateCd(String rateCd) {
		this.rateCd = rateCd;
	}
	public String getRateNm() {
		return rateNm;
	}
	public void setRateNm(String rateNm) {
		this.rateNm = rateNm;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getVasCd() {
		return vasCd;
	}
	public void setVasCd(String vasCd) {
		this.vasCd = vasCd;
	}
	public String getVasNm() {
		return vasNm;
	}
	public void setVasNm(String vasNm) {
		this.vasNm = vasNm;
	}
	public String getDupYn() {
		return dupYn;
	}
	public void setDupYn(String dupYn) {
		this.dupYn = dupYn;
	}
	public String getBaseAmt() {
		return baseAmt;
	}
	public void setBaseAmt(String baseAmt) {
		this.baseAmt = baseAmt;
	}
	public String getOnOffType() {
		return onOffType;
	}
	public void setOnOffType(String onOffType) {
		this.onOffType = onOffType;
	}
	public String getOnOffTypeNm() {
		return onOffTypeNm;
	}
	public void setOnOffTypeNm(String onOffTypeNm) {
		this.onOffTypeNm = onOffTypeNm;
	}
	public String getStatNm() {
		return statNm;
	}
	public void setStatNm(String statNm) {
		this.statNm = statNm;
	}
	
}
