package com.ktmmobile.mcp.mypage.dto;

import java.io.Serializable;

public class OwnPhoNumDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cstmrName;
    private String cstmrNativeRrn;
    private String cstmrNativeRrn01;
    private String cstmrNativeRrn02;
    private String onlineAuthType;
    private String onlineAuthInfo;
    private String cntrMobileNo;
    private String lstComActvDate;    
    
	private String reqSeq;				//요청일련번호
	private String resSeq;              //응답일련번호

	public String getCntrMobileNo() {
		return cntrMobileNo;
	}
	public void setCntrMobileNo(String cntrMobileNo) {
		this.cntrMobileNo = cntrMobileNo;
	}
	public String getLstComActvDate() {
		return lstComActvDate;
	}
	public void setLstComActvDate(String lstComActvDate) {
		this.lstComActvDate = lstComActvDate;
	}
	public String getCstmrName() {
		return cstmrName;
	}
	public void setCstmrName(String cstmrName) {
		this.cstmrName = cstmrName;
	}
	public String getCstmrNativeRrn() {
		return cstmrNativeRrn;
	}
	public void setCstmrNativeRrn(String cstmrNativeRrn) {
		this.cstmrNativeRrn = cstmrNativeRrn;
	}
	public String getCstmrNativeRrn01() {
		return cstmrNativeRrn01;
	}
	public void setCstmrNativeRrn01(String cstmrNativeRrn01) {
		this.cstmrNativeRrn01 = cstmrNativeRrn01;
	}
	public String getCstmrNativeRrn02() {
		return cstmrNativeRrn02;
	}
	public void setCstmrNativeRrn02(String cstmrNativeRrn02) {
		this.cstmrNativeRrn02 = cstmrNativeRrn02;
	}
	public String getOnlineAuthType() {
		return onlineAuthType;
	}
	public void setOnlineAuthType(String onlineAuthType) {
		this.onlineAuthType = onlineAuthType;
	}
	public String getOnlineAuthInfo() {
		return onlineAuthInfo;
	}
	public void setOnlineAuthInfo(String onlineAuthInfo) {
		this.onlineAuthInfo = onlineAuthInfo;
	}
    
	public String getReqSeq() {
		return reqSeq;
	}

	public void setReqSeq(String reqSeq) {
		this.reqSeq = reqSeq;
	}

	public String getResSeq() {
		return resSeq;
	}

	public void setResSeq(String resSeq) {
		this.resSeq = resSeq;
	}
}
