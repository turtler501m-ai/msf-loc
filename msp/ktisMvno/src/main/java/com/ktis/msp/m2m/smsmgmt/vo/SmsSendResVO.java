package com.ktis.msp.m2m.smsmgmt.vo;

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
	// 20241116 kt SMS 여부 추가(이승국)
	private String ktSmsYn;
	
	List<SmsSendResVO> items;
	
	// 마스킹
	private String custNmMsk;
	private String mobileNoMsk;	
	
	public String getCustNmMsk() {
		return custNmMsk;
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
	public String getKtSmsYn() {
		return ktSmsYn;
	}
	public void setKtSmsYn(String ktSmsYn) {
		this.ktSmsYn = ktSmsYn;
	}
	
}