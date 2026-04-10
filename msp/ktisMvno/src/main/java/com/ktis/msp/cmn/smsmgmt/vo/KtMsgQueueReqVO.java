package com.ktis.msp.cmn.smsmgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class KtMsgQueueReqVO extends BaseVo {

	private static final long serialVersionUID = 1206362043208533410L;
	private String msgId;
	
	private String subject;
	private String msgType;
	private String msgTypeSecond;
	private String msgTypeThird;
	private String scheduleTime;
	private String submitTime;
	private String message;
	private String callbackNum;
	private String rcptData;
	private String fileCount;
	private String fileName1;
	private String fileName2;
	private String fileName3;
	private String cdrId;
	private String failSend;
	
	private String kTmplcode;
	private String kMessage;
	private String kButton;
	private String kAdflag;
	private String kSenderkey;
	
	private String rMessagebaseid;
	private String rBody;
	private String rButton;
	private String rHeader;
	private String rFooter;
	private String rCopyallowed;
	private String rQueryid;
	private String reserved01; //시스템 - MSP, MCP, BATCH, CTI, RPA
	private String reserved02; //메뉴 or 시스템ID - PPS3000000, IntmInsrCanSchedule 
	private String reserved03; //템플릿 OR 사용자ID
	private String kisaOrigCode;
	private String rAgencyid;
	private String rAgencykey;
	private String rBrandkey;
	private String rCdrId;

	private String sendSeq;
	private String userId;
	private String templateId;
	private String sendType;
	private String status;
	
	private String templateSubject;
	private String templateText;
	
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMsgTypeSecond() {
		return msgTypeSecond;
	}
	public void setMsgTypeSecond(String msgTypeSecond) {
		this.msgTypeSecond = msgTypeSecond;
	}
	public String getMsgTypeThird() {
		return msgTypeThird;
	}
	public void setMsgTypeThird(String msgTypeThird) {
		this.msgTypeThird = msgTypeThird;
	}
	public String getScheduleTime() {
		return scheduleTime;
	}
	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCallbackNum() {
		return callbackNum;
	}
	public void setCallbackNum(String callbackNum) {
		this.callbackNum = callbackNum;
	}
	public String getRcptData() {
		return rcptData;
	}
	public void setRcptData(String rcptData) {
		this.rcptData = rcptData;
	}
	public String getFileCount() {
		return fileCount;
	}
	public void setFileCount(String fileCount) {
		this.fileCount = fileCount;
	}
	public String getFileName1() {
		return fileName1;
	}
	public void setFileName1(String fileName1) {
		this.fileName1 = fileName1;
	}
	public String getFileName2() {
		return fileName2;
	}
	public void setFileName2(String fileName2) {
		this.fileName2 = fileName2;
	}
	public String getFileName3() {
		return fileName3;
	}
	public void setFileName3(String fileName3) {
		this.fileName3 = fileName3;
	}
	public String getCdrId() {
		return cdrId;
	}
	public void setCdrId(String cdrId) {
		this.cdrId = cdrId;
	}
	public String getFailSend() {
		return failSend;
	}
	public void setFailSend(String failSend) {
		this.failSend = failSend;
	}
	public String getkTmplcode() {
		return kTmplcode;
	}
	public void setkTmplcode(String kTmplcode) {
		this.kTmplcode = kTmplcode;
	}
	public String getkMessage() {
		return kMessage;
	}
	public void setkMessage(String kMessage) {
		this.kMessage = kMessage;
	}
	public String getkButton() {
		return kButton;
	}
	public void setkButton(String kButton) {
		this.kButton = kButton;
	}
	public String getkAdflag() {
		return kAdflag;
	}
	public void setkAdflag(String kAdflag) {
		this.kAdflag = kAdflag;
	}
	public String getkSenderkey() {
		return kSenderkey;
	}
	public void setkSenderkey(String kSenderkey) {
		this.kSenderkey = kSenderkey;
	}
	public String getrMessagebaseid() {
		return rMessagebaseid;
	}
	public void setrMessagebaseid(String rMessagebaseid) {
		this.rMessagebaseid = rMessagebaseid;
	}
	public String getrBody() {
		return rBody;
	}
	public void setrBody(String rBody) {
		this.rBody = rBody;
	}
	public String getrButton() {
		return rButton;
	}
	public void setrButton(String rButton) {
		this.rButton = rButton;
	}
	public String getrHeader() {
		return rHeader;
	}
	public void setrHeader(String rHeader) {
		this.rHeader = rHeader;
	}
	public String getrFooter() {
		return rFooter;
	}
	public void setrFooter(String rFooter) {
		this.rFooter = rFooter;
	}
	public String getrCopyallowed() {
		return rCopyallowed;
	}
	public void setrCopyallowed(String rCopyallowed) {
		this.rCopyallowed = rCopyallowed;
	}
	public String getrQueryid() {
		return rQueryid;
	}
	public void setrQueryid(String rQueryid) {
		this.rQueryid = rQueryid;
	}
	public String getReserved01() {
		return reserved01;
	}
	public void setReserved01(String reserved01) {
		this.reserved01 = reserved01;
	}
	public String getReserved02() {
		return reserved02;
	}
	public void setReserved02(String reserved02) {
		this.reserved02 = reserved02;
	}
	public String getReserved03() {
		return reserved03;
	}
	public void setReserved03(String reserved03) {
		this.reserved03 = reserved03;
	}
	public String getKisaOrigCode() {
		return kisaOrigCode;
	}
	public void setKisaOrigCode(String kisaOrigCode) {
		this.kisaOrigCode = kisaOrigCode;
	}
	public String getrAgencyid() {
		return rAgencyid;
	}
	public void setrAgencyid(String rAgencyid) {
		this.rAgencyid = rAgencyid;
	}
	public String getrAgencykey() {
		return rAgencykey;
	}
	public void setrAgencykey(String rAgencykey) {
		this.rAgencykey = rAgencykey;
	}
	public String getrBrandkey() {
		return rBrandkey;
	}
	public void setrBrandkey(String rBrandkey) {
		this.rBrandkey = rBrandkey;
	}
	public String getrCdrId() {
		return rCdrId;
	}
	public void setrCdrId(String rCdrId) {
		this.rCdrId = rCdrId;
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
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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

    public SmsSendVO toSmsSendVO() {
        SmsSendVO smsSendVO = new SmsSendVO();
        smsSendVO.setTemplateId(this.templateId);
        smsSendVO.setMsgId(this.msgId);
        smsSendVO.setSendReqDttm(this.scheduleTime);
        smsSendVO.setReqId(this.userId);
        return smsSendVO;
    }
}
