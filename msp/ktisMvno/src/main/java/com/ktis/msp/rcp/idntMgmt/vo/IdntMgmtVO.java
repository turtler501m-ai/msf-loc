package com.ktis.msp.rcp.idntMgmt.vo;

import java.io.Serializable;
import java.util.List;

import com.ktis.msp.base.mvc.BaseVo;

public class IdntMgmtVO extends BaseVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// 조회
	private String searchGb;	//검색구분
	private String searchVal;	//검색어
	private String invstType;	//접수처유형
	private String invstVal;	//접수처
	private String reqDoc;	//공문서번호
	private String reqType;	//요청종류
	private String procDateS;
	private String procDateE;
	
	// 저장
	private String seqNum;
	private String searchType;
	private String contractNum;
	private String customerId;
	private String subscriberNo;
	private String cstmrNm;
	private String customerType;
	private String userSsn;
	private String subStatus;
	private String pppo;
	private String openDt;
	private String tmntDt;
	private String cstmrAddr;
	private String invstNm;
	private String invstLoc;
	
	private String fileName;
	private String excelYn;
	private String reqTypeNm;
	private String invstTypeNm;
	private String invstLocNm;	
	
	//20190924추가
	private String reqOdty;
	private String reqUser;
	private String appOdty;
	private String appUser;
	private String reqRsn;
	private String reqDttm;
	
	//20200925추가
	private List<String> seqNumList;
	private String seqNumStr;
	
	// 20201109 추가
	private String evntNm;
	private String evntChangeDate;
	
	
	private String appOdtyNm;
	
	//20201119 CNRK
	private String email;
	
	public String getReqTypeNm() {
		return reqTypeNm;
	}
	public void setReqTypeNm(String reqTypeNm) {
		this.reqTypeNm = reqTypeNm;
	}
	public String getInvstTypeNm() {
		return invstTypeNm;
	}
	public void setInvstTypeNm(String invstTypeNm) {
		this.invstTypeNm = invstTypeNm;
	}
	public String getInvstLocNm() {
		return invstLocNm;
	}
	public void setInvstLocNm(String invstLocNm) {
		this.invstLocNm = invstLocNm;
	}
	public String getExcelYn() {
		return excelYn;
	}
	public void setExcelYn(String excelYn) {
		this.excelYn = excelYn;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSearchGb() {
		return searchGb;
	}
	public void setSearchGb(String searchGb) {
		this.searchGb = searchGb;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	public String getInvstType() {
		return invstType;
	}
	public void setInvstType(String invstType) {
		this.invstType = invstType;
	}
	public String getInvstVal() {
		return invstVal;
	}
	public void setInvstVal(String invstVal) {
		this.invstVal = invstVal;
	}
	public String getReqDoc() {
		return reqDoc;
	}
	public void setReqDoc(String reqDoc) {
		this.reqDoc = reqDoc;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	public String getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	public String getCstmrNm() {
		return cstmrNm;
	}
	public void setCstmrNm(String cstmrNm) {
		this.cstmrNm = cstmrNm;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getUserSsn() {
		return userSsn;
	}
	public void setUserSsn(String userSsn) {
		this.userSsn = userSsn;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	public String getPppo() {
		return pppo;
	}
	public void setPppo(String pppo) {
		this.pppo = pppo;
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
	public String getCstmrAddr() {
		return cstmrAddr;
	}
	public void setCstmrAddr(String cstmrAddr) {
		this.cstmrAddr = cstmrAddr;
	}
	public String getInvstNm() {
		return invstNm;
	}
	public void setInvstNm(String invstNm) {
		this.invstNm = invstNm;
	}
	public String getInvstLoc() {
		return invstLoc;
	}
	public void setInvstLoc(String invstLoc) {
		this.invstLoc = invstLoc;
	}
	public String getProcDateS() {
		return procDateS;
	}
	public void setProcDateS(String procDateS) {
		this.procDateS = procDateS;
	}
	public String getProcDateE() {
		return procDateE;
	}
	public void setProcDateE(String procDateE) {
		this.procDateE = procDateE;
	}
	public String getReqOdty() {
		return reqOdty;
	}
	public void setReqOdty(String reqOdty) {
		this.reqOdty = reqOdty;
	}
	public String getReqUser() {
		return reqUser;
	}
	public void setReqUser(String reqUser) {
		this.reqUser = reqUser;
	}
	public String getAppOdty() {
		return appOdty;
	}
	public void setAppOdty(String appOdty) {
		this.appOdty = appOdty;
	}
	public String getAppUser() {
		return appUser;
	}
	public void setAppUser(String appUser) {
		this.appUser = appUser;
	}
	public String getReqRsn() {
		return reqRsn;
	}
	public void setReqRsn(String reqRsn) {
		this.reqRsn = reqRsn;
	}
	public String getReqDttm() {
		return reqDttm;
	}
	public void setReqDttm(String reqDttm) {
		this.reqDttm = reqDttm;
	}
	public String getAppOdtyNm() {
		return appOdtyNm;
	}
	public void setAppOdtyNm(String appOdtyNm) {
		this.appOdtyNm = appOdtyNm;
	}
	
	public List<String> getSeqNumList() {
		return seqNumList;
	}
	public void setSeqNumList(List<String> seqNumList) {
		this.seqNumList = seqNumList;
	}
	public String getSeqNumStr() {
		return seqNumStr;
	}
	public void setSeqNumStr(String seqNumStr) {
		this.seqNumStr = seqNumStr;
	}
	public String getEvntNm() {
		return evntNm;
	}
	public void setEvntNm(String evntNm) {
		this.evntNm = evntNm;
	}
	public String getEvntChangeDate() {
		return evntChangeDate;
	}
	public void setEvntChangeDate(String evntChangeDate) {
		this.evntChangeDate = evntChangeDate;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
