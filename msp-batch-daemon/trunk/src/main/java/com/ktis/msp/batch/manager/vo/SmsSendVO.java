package com.ktis.msp.batch.manager.vo;

public class SmsSendVO {
	
	private String sendDivision;
	private String contractNum;
	private String portalUserId;
	private String mspUserId;
	private String resNo;
	private String templateId;
	private String mseq;
	private String requestTime;
	private String reqId;
	
	private String ktSmsYn;
	//CMN_TERMS_SMS_SEND_MST 테이블 업데이트
	private String seqNum;
	//MSP_GIFT_PRMT_CUSTOMER 테이블 업데이트
	private String prmtId;
	//MSP_SMS_RESEND_MST 테이블 추가
	private String msgId;
	private String sendReqDttm;
	public String getSendDivision() {
		return sendDivision;
	}
	public void setSendDivision(String sendDivision) {
		this.sendDivision = sendDivision;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getPortalUserId() {
		return portalUserId;
	}
	public void setPortalUserId(String portalUserId) {
		this.portalUserId = portalUserId;
	}
	public String getMspUserId() {
		return mspUserId;
	}
	public void setMspUserId(String mspUserId) {
		this.mspUserId = mspUserId;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getMseq() {
		return mseq;
	}
	public void setMseq(String mseq) {
		this.mseq = mseq;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public String getKtSmsYn() {
		return ktSmsYn;
	}
	public void setKtSmsYn(String ktSmsYn) {
		this.ktSmsYn = ktSmsYn;
	}
	public String getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
	public String getPrmtId() {
		return prmtId;
	}
	public void setPrmtId(String prmtId) {
		this.prmtId = prmtId;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getSendReqDttm() {
		return sendReqDttm;
	}
	public void setSendReqDttm(String sendReqDttm) {
		this.sendReqDttm = sendReqDttm;
	}
	
}
