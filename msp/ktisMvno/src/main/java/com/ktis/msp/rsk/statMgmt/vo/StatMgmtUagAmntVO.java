package com.ktis.msp.rsk.statMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class StatMgmtUagAmntVO extends BaseVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9209587318395316803L;
	private String contractNum;
	private String custNm;
	private String subscriberNo;
	private String subStatus;
	private String subStatusNm;
	private String openDt;
	private String tmntDt;
	private String openAgntCd;
	private String openAgntNm;
	private String callUsgAmnt;
	private String callFreeAmnt;
	private String callOverAmnt;
	private String charUsgCnt;
	private String charFreeCnt;
	private String charOverCnt;
	private String dataUsgAmnt;
	private String dataFreeAmnt;
	private String dataOverAmnt;
	private String roamUsgAmnt;
	private String roamFreeAmnt;
	private String roamOverAmnt;
	
	public int TOTAL_COUNT;
	public String ROW_NUM;
	public String LINENUM;
	
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
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getCustNm() {
		return custNm;
	}
	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	public String getSubStatusNm() {
		return subStatusNm;
	}
	public void setSubStatusNm(String subStatusNm) {
		this.subStatusNm = subStatusNm;
	}
	public String getOpenDt() {
		return openDt;
	}
	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}
	public String getTmntDt() {
		return tmntDt;
	}
	public void setTmntDt(String tmntDt) {
		this.tmntDt = tmntDt;
	}
	public String getOpenAgntCd() {
		return openAgntCd;
	}
	public void setOpenAgntCd(String openAgntCd) {
		this.openAgntCd = openAgntCd;
	}
	public String getOpenAgntNm() {
		return openAgntNm;
	}
	public void setOpenAgntNm(String openAgntNm) {
		this.openAgntNm = openAgntNm;
	}
	public String getCallUsgAmnt() {
		return callUsgAmnt;
	}
	public void setCallUsgAmnt(String callUsgAmnt) {
		this.callUsgAmnt = callUsgAmnt;
	}
	public String getCallFreeAmnt() {
		return callFreeAmnt;
	}
	public void setCallFreeAmnt(String callFreeAmnt) {
		this.callFreeAmnt = callFreeAmnt;
	}
	public String getCallOverAmnt() {
		return callOverAmnt;
	}
	public void setCallOverAmnt(String callOverAmnt) {
		this.callOverAmnt = callOverAmnt;
	}
	public String getCharUsgCnt() {
		return charUsgCnt;
	}
	public void setCharUsgCnt(String charUsgCnt) {
		this.charUsgCnt = charUsgCnt;
	}
	public String getCharFreeCnt() {
		return charFreeCnt;
	}
	public void setCharFreeCnt(String charFreeCnt) {
		this.charFreeCnt = charFreeCnt;
	}
	public String getCharOverCnt() {
		return charOverCnt;
	}
	public void setCharOverCnt(String charOverCnt) {
		this.charOverCnt = charOverCnt;
	}
	public String getDataUsgAmnt() {
		return dataUsgAmnt;
	}
	public void setDataUsgAmnt(String dataUsgAmnt) {
		this.dataUsgAmnt = dataUsgAmnt;
	}
	public String getDataFreeAmnt() {
		return dataFreeAmnt;
	}
	public void setDataFreeAmnt(String dataFreeAmnt) {
		this.dataFreeAmnt = dataFreeAmnt;
	}
	public String getDataOverAmnt() {
		return dataOverAmnt;
	}
	public void setDataOverAmnt(String dataOverAmnt) {
		this.dataOverAmnt = dataOverAmnt;
	}
	public String getRoamUsgAmnt() {
		return roamUsgAmnt;
	}
	public void setRoamUsgAmnt(String roamUsgAmnt) {
		this.roamUsgAmnt = roamUsgAmnt;
	}
	public String getRoamFreeAmnt() {
		return roamFreeAmnt;
	}
	public void setRoamFreeAmnt(String roamFreeAmnt) {
		this.roamFreeAmnt = roamFreeAmnt;
	}
	public String getRoamOverAmnt() {
		return roamOverAmnt;
	}
	public void setRoamOverAmnt(String roamOverAmnt) {
		this.roamOverAmnt = roamOverAmnt;
	}
}
