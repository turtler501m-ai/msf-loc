package com.ktis.msp.m2m.smsmgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class MsgQueueReqVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1206362043208533410L;
	private String mseq;
	private String mobileNo;
	private String subject;
	private String text;
	private String templateId;
	private String requestTime;
	
	private String sendSeq;
	private String userId;
	
	private String templateSubject;
	private String templateText;
	
	// 수신자선택발송
	private String contractNum;
	private String sendDivision;
	private String portalId;
	private String sendType;
	
	// 카카오 알림톡
	private String text2;
	private String kTemplateCode;
	private String kNextType;
	private String senderKey;	
	
	public String getText2() {
		return text2;
	}
	public void setText2(String text2) {
		this.text2 = text2;
	}	
	public String getkTemplateCode() {
		return kTemplateCode;
	}
	public void setkTemplateCode(String kTemplateCode) {
		this.kTemplateCode = kTemplateCode;
	}
	public String getkNextType() {
		return kNextType;
	}
	public void setkNextType(String kNextType) {
		this.kNextType = kNextType;
	}
	public String getSenderKey() {
		return senderKey;
	}
	public void setSenderKey(String senderKey) {
		this.senderKey = senderKey;
	}
	public String getMseq() {
		return mseq;
	}
	public void setMseq(String mseq) {
		this.mseq = mseq;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getSendSeq() {
		return sendSeq;
	}
	public void setSendSeq(String sendSeq) {
		this.sendSeq = sendSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTemplateSubject() {
		return templateSubject;
	}
	public void setTemplateSubject(String templateSubject) {
		this.templateSubject = templateSubject;
	}
	public String getTemplateText() {
		return templateText;
	}
	public void setTemplateText(String templateText) {
		this.templateText = templateText;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getSendDivision() {
		return sendDivision;
	}
	public void setSendDivision(String sendDivision) {
		this.sendDivision = sendDivision;
	}
	public String getPortalId() {
		return portalId;
	}
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	
}
