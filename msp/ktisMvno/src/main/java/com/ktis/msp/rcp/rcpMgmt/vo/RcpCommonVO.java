package com.ktis.msp.rcp.rcpMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;


public class RcpCommonVO extends BaseVo implements Serializable {
	
	
	private static final long serialVersionUID = -3735035622092394393L;
	/******************************/
    private String requestKey="";
    private String typeCode = "";
    private String cdId = "";
	private String lbl="";
	private String grpId="";
	/** 제조사 ID */
	private String mnfctId="";
	private String cdNm="";
	
	// 조직ID
	private String orgnId;
	// 구매유형
	private String reqBuyType;
	// 신규유형
	private String operType;
	// 모집경로
	private String onOffType;
	// 단말ID
	private String prdtId;
	// 단말ID
	private String serviceType;
	// 속하는 날짜
	private String reqInDay;
	
	//제품구분코드 eSim 제품 조회 
	private String prdtIndCd;

	
	public String getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getCdId() {
		return cdId;
	}
	public void setCdId(String cdId) {
		this.cdId = cdId;
	}
	public String getLbl() {
		return lbl;
	}
	public void setLbl(String lbl) {
		this.lbl = lbl;
	}
	public String getGrpId() {
		return grpId;
	}
	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}
	public String getMnfctId() {
		return mnfctId;
	}
	public void setMnfctId(String mnfctId) {
		this.mnfctId = mnfctId;
	}
	public String getCdNm() {
		return cdNm;
	}
	public void setCdNm(String cdNm) {
		this.cdNm = cdNm;
	}
	/**
	 * @return the orgnId
	 */
	public String getOrgnId() {
		return orgnId;
	}
	/**
	 * @param orgnId the orgnId to set
	 */
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	/**
	 * @return the reqBuyType
	 */
	public String getReqBuyType() {
		return reqBuyType;
	}
	/**
	 * @param reqBuyType the reqBuyType to set
	 */
	public void setReqBuyType(String reqBuyType) {
		this.reqBuyType = reqBuyType;
	}
	/**
	 * @return the operType
	 */
	public String getOperType() {
		return operType;
	}
	/**
	 * @param operType the operType to set
	 */
	public void setOperType(String operType) {
		this.operType = operType;
	}
	/**
	 * @return the onOffType
	 */
	public String getOnOffType() {
		return onOffType;
	}
	/**
	 * @param onOffType the onOffType to set
	 */
	public void setOnOffType(String onOffType) {
		this.onOffType = onOffType;
	}
	/**
	 * @return the prdtId
	 */
	public String getPrdtId() {
		return prdtId;
	}
	/**
	 * @param prdtId the prdtId to set
	 */
	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}
	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}
	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	/**
	 * @return the reqInDay
	 */
	public String getReqInDay() {
		return reqInDay;
	}
	/**
	 * @param reqInDay the reqInDay to set
	 */
	public void setReqInDay(String reqInDay) {
		this.reqInDay = reqInDay;
	}
	public String getPrdtIndCd() {
		return prdtIndCd;
	}
	public void setPrdtIndCd(String prdtIndCd) {
		this.prdtIndCd = prdtIndCd;
	}
	
}
