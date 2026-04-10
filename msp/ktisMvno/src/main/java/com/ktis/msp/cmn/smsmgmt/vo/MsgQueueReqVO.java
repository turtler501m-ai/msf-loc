package com.ktis.msp.cmn.smsmgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class MsgQueueReqVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1206362043208533410L;
	private String mseq;
	
	private String msgType;
	private String dstaddr;
	private String mobileNo;
	private String callback;
	private String subject;
	private String stat;
	private String textType;
	private String text;
	private String expiretime;
	private String requestTime;
	private String optId;
	private String extCol0;
	private String fileCnt;
	private String fileLoc1;
	private String fileLoc2;
	private String fileLoc3;
	private String fileLoc4;
	private String fileLoc5;
	private String templateId;
	
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
	public String getMseq() {
		return mseq;
	}
	public void setMseq(String mseq) {
		this.mseq = mseq;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getDstaddr() {
		return dstaddr;
	}
	public void setDstaddr(String dstaddr) {
		this.dstaddr = dstaddr;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public String getTextType() {
		return textType;
	}
	public void setTextType(String textType) {
		this.textType = textType;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getExpiretime() {
		return expiretime;
	}
	public void setExpiretime(String expiretime) {
		this.expiretime = expiretime;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getOptId() {
		return optId;
	}
	public void setOptId(String optId) {
		this.optId = optId;
	}
	public String getExtCol0() {
		return extCol0;
	}
	public void setExtCol0(String extCol0) {
		this.extCol0 = extCol0;
	}
	public String getFileCnt() {
		return fileCnt;
	}
	public void setFileCnt(String fileCnt) {
		this.fileCnt = fileCnt;
	}
	public String getFileLoc1() {
		return fileLoc1;
	}
	public void setFileLoc1(String fileLoc1) {
		this.fileLoc1 = fileLoc1;
	}
	public String getFileLoc2() {
		return fileLoc2;
	}
	public void setFileLoc2(String fileLoc2) {
		this.fileLoc2 = fileLoc2;
	}
	public String getFileLoc3() {
		return fileLoc3;
	}
	public void setFileLoc3(String fileLoc3) {
		this.fileLoc3 = fileLoc3;
	}
	public String getFileLoc4() {
		return fileLoc4;
	}
	public void setFileLoc4(String fileLoc4) {
		this.fileLoc4 = fileLoc4;
	}
	public String getFileLoc5() {
		return fileLoc5;
	}
	public void setFileLoc5(String fileLoc5) {
		this.fileLoc5 = fileLoc5;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	
	
}
