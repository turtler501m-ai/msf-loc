package com.ktis.msp.cmn.smsmgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class SmsSendVO extends BaseVo {
	
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
