package com.ktis.msp.batch.manager.vo;

public class SmsCommonVO {
	
	// AM2X_SUBMIT
	private String mseq;			// sms seq
	private String msgType;			// 1:SMS 2:SMS URL 3:MMS 4:MMS URL
	private String mobileNo;		// 고객 휴대폰번호
	private String sendNumber;		// 발신자 번호
	private String subject;			// 제목
	private String stat;			// 0=전송대기(초기값), 1=송신중 2=송신완료, 3=결과수신 
	private String textType;		// 0:plan 1:html 
	private String sendMessage;		// 전송 메시지
	private String expireTime;		// 유효시간
	private String requestTime;	// 발송요청시간
	private String templateId;		// SMS Template ID
	private String optId;		// SMS OPT ID
	
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
	
	private String text2;
	private String kTemplateCode;
	private String senderKey;
	private String kNextType;
	
	// 사은품 프로모션
	private String prmtId;
	
	// 대량(예약)문자 전송
	private String msgNo;
	private String sendCnt;
	private String resultYn;
	private String resultCode;
	private String kakaoYn;
	
	// 제세공과금 문자 전송
	private String taxNo;
	private String taxSendCnt;
	private String taxUsgYn;
	private String taxKakaoYn;
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
	public String getSenderKey() {
		return senderKey;
	}
	public void setSenderKey(String senderKey) {
		this.senderKey = senderKey;
	}
	public String getkNextType() {
		return kNextType;
	}
	public void setkNextType(String kNextType) {
		this.kNextType = kNextType;
	}
	public String getPrmtId() {
		return prmtId;
	}
	public void setPrmtId(String prmtId) {
		this.prmtId = prmtId;
	}
	public String getMsgNo() {
		return msgNo;
	}
	public void setMsgNo(String msgNo) {
		this.msgNo = msgNo;
	}
	public String getSendCnt() {
		return sendCnt;
	}
	public void setSendCnt(String sendCnt) {
		this.sendCnt = sendCnt;
	}
	public String getResultYn() {
		return resultYn;
	}
	public void setResultYn(String resultYn) {
		this.resultYn = resultYn;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getKakaoYn() {
		return kakaoYn;
	}
	public void setKakaoYn(String kakaoYn) {
		this.kakaoYn = kakaoYn;
	}
	public String getTaxNo() {
		return taxNo;
	}
	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}
	public String getTaxSendCnt() {
		return taxSendCnt;
	}
	public void setTaxSendCnt(String taxSendCnt) {
		this.taxSendCnt = taxSendCnt;
	}
	public String getTaxUsgYn() {
		return taxUsgYn;
	}
	public void setTaxUsgYn(String taxUsgYn) {
		this.taxUsgYn = taxUsgYn;
	}
	public String getTaxKakaoYn() {
		return taxKakaoYn;
	}
	public void setTaxKakaoYn(String taxKakaoYn) {
		this.taxKakaoYn = taxKakaoYn;
	}
	public String getOptId() {
		return optId;
	}
	public void setOptId(String optId) {
		this.optId = optId;
	}
	
}
