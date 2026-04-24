package com.ktis.msp.batch.manager.vo;

public class SmsCommonVO {
	
	// MSG_QUEUE
	private String mseq;			// sms seq
	private String msgType;			// 1:SMS 2:SMS URL 3:MMS 4:MMS URL
	private String mobileNo;		// 고객 휴대폰번호
	private String sendNumber;		// 발신자 번호
	private String subject;			// 제목
	private String sendMessage;		// 전송 메시지
	private String expireTime;		// 유효시간
	private String requestTime;	// 발송요청시간
	private String templateId;		// SMS Template ID
	
	// MSP_SMS_SEND_MST
	private Integer sendSeq;		// SEND SEQ
	private String sendDivision;	// MCP : 홈페이지 , MSP : M전산(가입고객) , USR : 본사사용자
	private String contractNum;		// 계약번호
	private String portalUserid;	// 홈페이지사용자ID
	private String mspUserId;		// M전산사용자ID
	private String reqId;			// 요청자ID
	
	private String totalCount;		// 전체건수
	private String rn;				// ROWNUM
	
	// 마케팅
	private String strtDttm;		// 동의시작일시
	private String agrDt;			// 동의일자
	
	// 템플릿
	private String templateText;
	private String templateSubject;
	
	private String seqNum;
	private String regstDttm;
	private String userId;
	
	
	
	public String getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
	public String getRegstDttm() {
		return regstDttm;
	}
	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public Integer getSendSeq() {
		return sendSeq;
	}
	public void setSendSeq(Integer sendSeq) {
		this.sendSeq = sendSeq;
	}
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
	public String getPortalUserid() {
		return portalUserid;
	}
	public void setPortalUserid(String portalUserid) {
		this.portalUserid = portalUserid;
	}
	public String getMspUserId() {
		return mspUserId;
	}
	public void setMspUserId(String mspUserId) {
		this.mspUserId = mspUserId;
	}
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
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
	public String getTemplateText() {
		return templateText;
	}
	public void setTemplateText(String templateText) {
		this.templateText = templateText;
	}
	public String getTemplateSubject() {
		return templateSubject;
	}
	public void setTemplateSubject(String templateSubject) {
		this.templateSubject = templateSubject;
	}
	
}
