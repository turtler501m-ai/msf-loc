package com.ktis.msp.cmn.smsmgmt.vo;

import java.util.List;

import com.ktis.msp.base.mvc.BaseVo;

public class SmsSendResVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1976449887365520221L;
	
	private String sendReqDttm;			// 발송요청일시
	private String workType;			// 업무구분
	private String workTypeNm;			// 업무구분명
	private String receiveNm;			// 수신자명
	private String mobileNo;			// 수신자번호
	private String maskMobileNo;		// MASKING 수신자번호
	private String sendDivision;		// 발송고객구분(MCP:홈페이지,MSP:가입고객,USR:M전산사용자)
	private String sendDivisionNm;		// 발송고객구분명
	private String contractNum;			// 계약번호
	private String portalUserId;		// 홈페이지ID
	private String mspUserId;			// M전산사용자ID
	private String resNo;				// 예약번호
	private String templateId;			// 템플릿ID
	private String templateNm;			// 템플릿명
	private String sendYn;				// 발송여부
	private String sendYnNm;			// 발송여부
	private String result;				// 발송결과코드
	private String resultNm;			// 발송결과
	private Integer sendCnt;			// 발송횟수
	private String sendTime;			// 발송일시
	private String procId;				// 처리자ID
	private String procNm;				// 처리자명
	private String text;				// 발송내용
	private String mseq;				// SMS일련번호
	private String sendSeq;				// 발송일련번호
	
	// 수신자선택발송
	private String custDiv;
	private String portalId;
	private String reqInDay;
	private String custNm;
	private String reqStateNm;
	private String sysRdate;
	private String emailAgr;
	private String smsAgr;
	private String openDt;
	private String rateNm;
	private String buyTypeNm;
	private String marketingCd;
	private String addService;
	private String sendType;
	private String requestTime;
	private String fileName;
	
	
	// 마스킹
	private String custNmMsk;
	private String mobileNoMsk;	
	
	
	//대량문자 발송
	private String srchStrtDt;
    private String srchEndDt;
	private String searchName;
	private String searchGbn;
	
	
	
	private String msgNo;

	private String subject;
	private String sendCtn;
	private String reustTime;
	private String sessionUserId;
	private String rvisnId;
	private String msgType;
	private String regstId;
	private String msgSendCtn;
	private String msgRecvCtn;
	private String msgTitle;
	private String msgText;
	private String msgTmpId;
	private String resultYn;
	private String resultYnNm;
	private String msgUsgYn;
	private String msgSmsType;
	private String kakaoYn;
	
	//KT SMS 전환
	private String submitTime;
	private String scheduleTime;
	private String rcptData;
	private String callbackNum;
	private String reserved01;
	private String reserved02;
	private String reserved03;
	private String kMessage;
	private String kTmplcode;
	private String kSenderkey;
	private String message;
	
	List<SmsSendResVO> items;

	//페이징
	//public int TOTAL_COUNT;
	//public String ROW_NUM;
	//public String LINENUM;
    
	public String getKakaoYn() {
		return kakaoYn;
	}
	public void setKakaoYn(String kakaoYn) {
		this.kakaoYn = kakaoYn;
	}
	
	public String getResultYn() {
		return resultYn;
	}
	public String getMsgTmpId() {
		return msgTmpId;
	}
	public void setMsgTmpId(String msgTmpId) {
		this.msgTmpId = msgTmpId;
	}
	public void setResultYn(String resultYn) {
		this.resultYn = resultYn;
	}
	public String getResultYnNm() {
		return resultYnNm;
	}
	public void setResultYnNm(String resultYnNm) {
		this.resultYnNm = resultYnNm;
	}
	public String getMsgUsgYn() {
		return msgUsgYn;
	}
	public void setMsgUsgYn(String msgUsgYn) {
		this.msgUsgYn = msgUsgYn;
	}
	public String getMsgSmsType() {
		return msgSmsType;
	}
	public void setMsgSmsType(String msgSmsType) {
		this.msgSmsType = msgSmsType;
	}
	public String getMsgSendCtn() {
		return msgSendCtn;
	}
	public void setMsgSendCtn(String msgSendCtn) {
		this.msgSendCtn = msgSendCtn;
	}
	public String getMsgRecvCtn() {
		return msgRecvCtn;
	}
	public void setMsgRecvCtn(String msgRecvCtn) {
		this.msgRecvCtn = msgRecvCtn;
	}
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	public String getMsgText() {
		return msgText;
	}
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	public String getRegstId() {
		return regstId;
	}
	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}
	public String getRvisnId() {
		return rvisnId;
	}
	
	public String getMsgNo() {
		return msgNo;
	}
	public void setMsgNo(String msgNo) {
		this.msgNo = msgNo;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSendCtn() {
		return sendCtn;
	}
	public void setSendCtn(String sendCtn) {
		this.sendCtn = sendCtn;
	}
	public String getReustTime() {
		return reustTime;
	}
	public void setReustTime(String reustTime) {
		this.reustTime = reustTime;
	}
	public String getSessionUserId() {
		return sessionUserId;
	}
	public void setSessionUserId(String sessionUserId) {
		this.sessionUserId = sessionUserId;
	}
	public String getCustNmMsk() {
		return custNmMsk;
	}
	public String getSrchStrtDt() {
		return srchStrtDt;
	}
	public void setSrchStrtDt(String srchStrtDt) {
		this.srchStrtDt = srchStrtDt;
	}
	public String getSrchEndDt() {
		return srchEndDt;
	}
	public void setSrchEndDt(String srchEndDt) {
		this.srchEndDt = srchEndDt;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getSearchGbn() {
		return searchGbn;
	}
	public void setSearchGbn(String searchGbn) {
		this.searchGbn = searchGbn;
	}
	public void setCustNmMsk(String custNmMsk) {
		this.custNmMsk = custNmMsk;
	}
	public String getMobileNoMsk() {
		return mobileNoMsk;
	}
	public void setMobileNoMsk(String mobileNoMsk) {
		this.mobileNoMsk = mobileNoMsk;
	}
	public String getSendReqDttm() {
		return sendReqDttm;
	}
	public void setSendReqDttm(String sendReqDttm) {
		this.sendReqDttm = sendReqDttm;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	public String getWorkTypeNm() {
		return workTypeNm;
	}
	public void setWorkTypeNm(String workTypeNm) {
		this.workTypeNm = workTypeNm;
	}
	public String getReceiveNm() {
		return receiveNm;
	}
	public void setReceiveNm(String receiveNm) {
		this.receiveNm = receiveNm;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getMaskMobileNo() {
		return maskMobileNo;
	}
	public void setMaskMobileNo(String maskMobileNo) {
		this.maskMobileNo = maskMobileNo;
	}
	public String getSendDivision() {
		return sendDivision;
	}
	public void setSendDivision(String sendDivision) {
		this.sendDivision = sendDivision;
	}
	public String getSendDivisionNm() {
		return sendDivisionNm;
	}
	public void setSendDivisionNm(String sendDivisionNm) {
		this.sendDivisionNm = sendDivisionNm;
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
	public String getTemplateNm() {
		return templateNm;
	}
	public void setTemplateNm(String templateNm) {
		this.templateNm = templateNm;
	}
	public String getSendYn() {
		return sendYn;
	}
	public void setSendYn(String sendYn) {
		this.sendYn = sendYn;
	}
	public String getSendYnNm() {
		return sendYnNm;
	}
	public void setSendYnNm(String sendYnNm) {
		this.sendYnNm = sendYnNm;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResultNm() {
		return resultNm;
	}
	public void setResultNm(String resultNm) {
		this.resultNm = resultNm;
	}
	public Integer getSendCnt() {
		return sendCnt;
	}
	public void setSendCnt(Integer sendCnt) {
		this.sendCnt = sendCnt;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
	public String getProcNm() {
		return procNm;
	}
	public void setProcNm(String procNm) {
		this.procNm = procNm;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getMseq() {
		return mseq;
	}
	public void setMseq(String mseq) {
		this.mseq = mseq;
	}
	public String getSendSeq() {
		return sendSeq;
	}
	public void setSendSeq(String sendSeq) {
		this.sendSeq = sendSeq;
	}
	public String getCustDiv() {
		return custDiv;
	}
	public void setCustDiv(String custDiv) {
		this.custDiv = custDiv;
	}
	public String getPortalId() {
		return portalId;
	}
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
	public String getReqInDay() {
		return reqInDay;
	}
	public void setReqInDay(String reqInDay) {
		this.reqInDay = reqInDay;
	}
	public String getCustNm() {
		return custNm;
	}
	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}
	public String getReqStateNm() {
		return reqStateNm;
	}
	public void setReqStateNm(String reqStateNm) {
		this.reqStateNm = reqStateNm;
	}
	public String getSysRdate() {
		return sysRdate;
	}
	public void setSysRdate(String sysRdate) {
		this.sysRdate = sysRdate;
	}
	public String getEmailAgr() {
		return emailAgr;
	}
	public void setEmailAgr(String emailAgr) {
		this.emailAgr = emailAgr;
	}
	public String getSmsAgr() {
		return smsAgr;
	}
	public void setSmsAgr(String smsAgr) {
		this.smsAgr = smsAgr;
	}
	public String getOpenDt() {
		return openDt;
	}
	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}
	public String getRateNm() {
		return rateNm;
	}
	public void setRateNm(String rateNm) {
		this.rateNm = rateNm;
	}
	public String getBuyTypeNm() {
		return buyTypeNm;
	}
	public void setBuyTypeNm(String buyTypeNm) {
		this.buyTypeNm = buyTypeNm;
	}
	public String getMarketingCd() {
		return marketingCd;
	}
	public void setMarketingCd(String marketingCd) {
		this.marketingCd = marketingCd;
	}
	public String getAddService() {
		return addService;
	}
	public void setAddService(String addService) {
		this.addService = addService;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<SmsSendResVO> getItems() {
		return items;
	}
	public void setItems(List<SmsSendResVO> items) {
		this.items = items;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	public String getScheduleTime() {
		return scheduleTime;
	}
	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	public String getRcptData() {
		return rcptData;
	}
	public void setRcptData(String rcptData) {
		this.rcptData = rcptData;
	}
	public String getCallbackNum() {
		return callbackNum;
	}
	public void setCallbackNum(String callbackNum) {
		this.callbackNum = callbackNum;
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
	public String getkMessage() {
		return kMessage;
	}
	public void setkMessage(String kMessage) {
		this.kMessage = kMessage;
	}
	public String getkTmplcode() {
		return kTmplcode;
	}
	public void setkTmplcode(String kTmplcode) {
		this.kTmplcode = kTmplcode;
	}
	public String getkSenderkey() {
		return kSenderkey;
	}
	public void setkSenderkey(String kSenderkey) {
		this.kSenderkey = kSenderkey;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}