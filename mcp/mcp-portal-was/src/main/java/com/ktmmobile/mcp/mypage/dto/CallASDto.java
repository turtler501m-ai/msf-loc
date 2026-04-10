package com.ktmmobile.mcp.mypage.dto;

import java.io.Serializable;

public class CallASDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ncn;
    private int reqSeq=0;
	private String userId;
	private String contractNum;
	private String cstmrName;
	private String mobileNo;
	private String errorStartDate;
	private String errorEndDate;
	private String errorTypeCd;
	private String errorVoice;
	private String errorCall;
	private String errorSms;
	private String errorWifi;
	private String errorData;
	private String errorLocTypeCd;
	private String cstmrPost;
	private String cstmrAddr;
	private String cstmrAddrDtl;
	private String regNm;
	private String regMobileNo;
	private String sysDt;
	private String regDt;


	public String getNcn() {
		return ncn;
	}
	public void setNcn(String ncn) {
		this.ncn = ncn;
	}
	public int getReqSeq() {
		return reqSeq;
	}
	public void setReqSeq(int reqSeq) {
		this.reqSeq = reqSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getCstmrName() {
		return cstmrName;
	}
	public void setCstmrName(String cstmrName) {
		this.cstmrName = cstmrName;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getErrorStartDate() {
		return errorStartDate;
	}
	public void setErrorStartDate(String errorStartDate) {
		this.errorStartDate = errorStartDate;
	}
	public String getErrorEndDate() {
		return errorEndDate;
	}
	public void setErrorEndDate(String errorEndDate) {
		this.errorEndDate = errorEndDate;
	}
	public String getErrorTypeCd() {
		return errorTypeCd;
	}
	public void setErrorTypeCd(String errorTypeCd) {
		this.errorTypeCd = errorTypeCd;
	}
	public String getErrorVoice() {
		return errorVoice;
	}
	public void setErrorVoice(String errorVoice) {
		this.errorVoice = errorVoice;
	}
	public String getErrorCall() {
		return errorCall;
	}
	public void setErrorCall(String errorCall) {
		this.errorCall = errorCall;
	}
	public String getErrorSms() {
		return errorSms;
	}
	public void setErrorSms(String errorSms) {
		this.errorSms = errorSms;
	}
	public String getErrorWifi() {
		return errorWifi;
	}
	public void setErrorWifi(String errorWifi) {
		this.errorWifi = errorWifi;
	}
	public String getErrorData() {
		return errorData;
	}
	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}
	public String getErrorLocTypeCd() {
		return errorLocTypeCd;
	}
	public void setErrorLocTypeCd(String errorLocTypeCd) {
		this.errorLocTypeCd = errorLocTypeCd;
	}
	public String getCstmrPost() {
		return cstmrPost;
	}
	public void setCstmrPost(String cstmrPost) {
		this.cstmrPost = cstmrPost;
	}
	public String getCstmrAddr() {
		return cstmrAddr;
	}
	public void setCstmrAddr(String cstmrAddr) {
		this.cstmrAddr = cstmrAddr;
	}
	public String getCstmrAddrDtl() {
		return cstmrAddrDtl;
	}
	public void setCstmrAddrDtl(String cstmrAddrDtl) {
		this.cstmrAddrDtl = cstmrAddrDtl;
	}
	public String getRegNm() {
		return regNm;
	}
	public void setRegNm(String regNm) {
		this.regNm = regNm;
	}
	public String getRegMobileNo() {
		return regMobileNo;
	}
	public void setRegMobileNo(String regMobileNo) {
		this.regMobileNo = regMobileNo;
	}
	public String getSysDt() {
		return sysDt;
	}
	public void setSysDt(String sysDt) {
		this.sysDt = sysDt;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}




}
