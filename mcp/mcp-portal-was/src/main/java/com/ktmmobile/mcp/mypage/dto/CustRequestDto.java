package com.ktmmobile.mcp.mypage.dto;

import java.io.Serializable;

public class CustRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
    
	private long requestKeyOrg;
	private String scanIdOrg;
	
	/**
	 * NMCP_CUST_REQUEST_MST  
	*/
    private long custReqSeq;
    private String reqType;
    private String userId;
    private String cstmrName;
    private String mobileNo;
    private String cstmrNativeRrn;
    private String contractNum;
    private String cstmrType;
    private String onlineAuthType;
    private String onlineAuthInfo;
    private String cretId;
    private String sysRdate;
    private String custId;
    
    /**
     * NMCP_CUST_REQUEST_CALL_LIST 
    */
    private String scanId;
    private String startDate;
    private String endDate;
    private String callType;
    private String typeVoice;
    private String typeText;
    private String typeData;
    private String typeVoiceText;
    private String typeAll;
    private String reqRsn;
    private String recvType;
    private String recvText;
    private String callNum;
    private String mailAddr;
    private String etcMemo;
    private String updtId;
    private String sysUdate;
    
    /**
     * NMCP_CUST_REQUEST_JOIN_FORM
    */
    private String faxNo;
    private String cstmrPost;
    private String cstmrAddr;
    private String cstmrAddrDtl;
    
    /**
     * 안심보험 가입신청
     */
    private String insrType;
    private String insrProdCd;
    private String reqBuyType;
    private int maxFileNum;
    private String etcMobile;
    
	/** CERT */
	private String reqSeq;
	private String resSeq;
	private String ncn;

	public String getInsrType() {
		return insrType;
	}
	public void setInsrType(String insrType) {
		this.insrType = insrType;
	}
    public String getInsrProdCd() {
		return insrProdCd;
	}
	public void setInsrProdCd(String insrProdCd) {
		this.insrProdCd = insrProdCd;
	}
	public String getReqBuyType() {
		return reqBuyType;
	}
	public void setReqBuyType(String reqBuyType) {
		this.reqBuyType = reqBuyType;
	}
    
    public long getRequestKeyOrg() {
		return requestKeyOrg;
	}
	public void setRequestKeyOrg(long requestKeyOrg) {
		this.requestKeyOrg = requestKeyOrg;
	}
	public String getScanIdOrg() {
		return scanIdOrg;
	}
	public void setScanIdOrg(String scanIdOrg) {
		this.scanIdOrg = scanIdOrg;
	}
	public long getCustReqSeq() {
		return custReqSeq;
	}
	public void setCustReqSeq(long custReqSeq) {
		this.custReqSeq = custReqSeq;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getCstmrType() {
		return cstmrType;
	}
	public void setCstmrType(String cstmrType) {
		this.cstmrType = cstmrType;
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
	public String getCretId() {
		return cretId;
	}
	public void setCretId(String cretId) {
		this.cretId = cretId;
	}
	public String getSysRdate() {
		return sysRdate;
	}
	public void setSysRdate(String sysRdate) {
		this.sysRdate = sysRdate;
	}
	public String getScanId() {
		return scanId;
	}
	public void setScanId(String scanId) {
		this.scanId = scanId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCallType() {
		return callType;
	}
	public void setCallType(String callType) {
		this.callType = callType;
	}
	public String getTypeVoice() {
		return typeVoice;
	}
	public void setTypeVoice(String typeVoice) {
		this.typeVoice = typeVoice;
	}
	public String getTypeText() {
		return typeText;
	}
	public void setTypeText(String typeText) {
		this.typeText = typeText;
	}
	public String getTypeData() {
		return typeData;
	}
	public void setTypeData(String typeData) {
		this.typeData = typeData;
	}
	public String getTypeVoiceText() {
		return typeVoiceText;
	}
	public void setTypeVoiceText(String typeVoiceText) {
		this.typeVoiceText = typeVoiceText;
	}
	public String getTypeAll() {
		return typeAll;
	}
	public void setTypeAll(String typeAll) {
		this.typeAll = typeAll;
	}
	public String getReqRsn() {
		return reqRsn;
	}
	public void setReqRsn(String reqRsn) {
		this.reqRsn = reqRsn;
	}
	public String getRecvType() {
		return recvType;
	}
	public void setRecvType(String recvType) {
		this.recvType = recvType;
	}
	public String getRecvText() {
		return recvText;
	}
	public void setRecvText(String recvText) {
		this.recvText = recvText;
	}
	public String getCallNum() {
		return callNum;
	}
	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}
	public String getMailAddr() {
		return mailAddr;
	}
	public void setMailAddr(String mailAddr) {
		this.mailAddr = mailAddr;
	}
	public String getEtcMemo() {
		return etcMemo;
	}
	public void setEtcMemo(String etcMemo) {
		this.etcMemo = etcMemo;
	}
	public String getUpdtId() {
		return updtId;
	}
	public void setUpdtId(String updtId) {
		this.updtId = updtId;
	}
	public String getSysUdate() {
		return sysUdate;
	}
	public void setSysUdate(String sysUdate) {
		this.sysUdate = sysUdate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCstmrNativeRrn() {
		return cstmrNativeRrn;
	}
	public void setCstmrNativeRrn(String cstmrNativeRrn) {
		this.cstmrNativeRrn = cstmrNativeRrn;
	}
	public String getCstmrName() {
		return cstmrName;
	}
	public void setCstmrName(String cstmrName) {
		this.cstmrName = cstmrName;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
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
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public int getMaxFileNum() {
		return maxFileNum;
	}
	public void setMaxFileNum(int maxFileNum) {
		this.maxFileNum = maxFileNum;
	}
	public String getEtcMobile() {
		return etcMobile;
	}
	public void setEtcMobile(String etcMobile) {
		this.etcMobile = etcMobile;
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

	public String getNcn() {
		return ncn;
	}

	public void setNcn(String ncn) {
		this.ncn = ncn;
	}
}
