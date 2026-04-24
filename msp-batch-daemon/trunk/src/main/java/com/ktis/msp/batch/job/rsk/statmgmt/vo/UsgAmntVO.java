package com.ktis.msp.batch.job.rsk.statmgmt.vo;

import com.ktis.msp.base.BaseVo;

public class UsgAmntVO extends BaseVo {
	
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
	private String openYm;
	
	// 조회조건
	private String searchType;
	private String searchVal;
	private String usgYm;
	private String orgnId;
	
	// 엑셀다운로드 로그
	private String userId;
	private String dwnldRsn;	/*다운로드 사유*/
	private String ipAddr;		/*ip정보*/
	private String menuId;		/*메뉴ID*/
	private String exclDnldId;	
	
	public String getOpenYm() {
		return openYm;
	}
	public void setOpenYm(String openYm) {
		this.openYm = openYm;
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
	public String getUsgYm() {
		return usgYm;
	}
	public void setUsgYm(String usgYm) {
		this.usgYm = usgYm;
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
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDwnldRsn() {
		return dwnldRsn;
	}
	public void setDwnldRsn(String dwnldRsn) {
		this.dwnldRsn = dwnldRsn;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getExclDnldId() {
		return exclDnldId;
	}
	public void setExclDnldId(String exclDnldId) {
		this.exclDnldId = exclDnldId;
	}
	
}
