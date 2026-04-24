package com.ktis.msp.batch.job.rcp.mrktmgmt.vo;

import java.sql.Timestamp;

public class MarketingVO {
	
	private String sendDivision;	// MCP : 홈페이지 , MSP : M전산
	private String contractNum;	// 계약번호
	private String userid;			// 홈페이지사용자ID
	private String templateId;		// SMS Template ID
	private String mobileNo;		// 고객 휴대폰번호
	private String strtDttm;		// 동의시작일시
	private String agrDt;			// 동의일자
	private String mseq;			// sms seq
	private String msgType;		// 1:SMS 2:SMS URL 3:MMS 4:MMS URL
	private String sendNumber;		// 발신자 번호
	private String subject;		// 제목
	private String sendMessage;	// 전송 메시지
	private String expireTime;		// 유효시간
	private Timestamp requestTime;	// 발송요청시간
	private String totalCount;		// 전체건수
	private String rn;				// ROWNUM
	private String reqId;			// 요청자ID
	private Integer sendSeq;		// SEND SEQ
	
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
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getStrtDttm() {
		return strtDttm;
	}
	public void setStrtDttm(String strtDttm) {
		this.strtDttm = strtDttm;
	}
	public String getAgrDt() {
		return agrDt;
	}
	public void setAgrDt(String agrDt) {
		this.agrDt = agrDt;
	}
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
	public String getSendNumber() {
		return sendNumber;
	}
	public void setSendNumber(String sendNumber) {
		this.sendNumber = sendNumber;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSendMessage() {
		return sendMessage;
	}
	public void setSendMessage(String sendMessage) {
		this.sendMessage = sendMessage;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	public Timestamp getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getRn() {
		return rn;
	}
	public void setRn(String rn) {
		this.rn = rn;
	}
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public Integer getSendSeq() {
		return sendSeq;
	}
	public void setSendSeq(Integer sendSeq) {
		this.sendSeq = sendSeq;
	}
	
}
