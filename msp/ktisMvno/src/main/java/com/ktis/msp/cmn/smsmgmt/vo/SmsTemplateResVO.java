package com.ktis.msp.cmn.smsmgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class SmsTemplateResVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4033248386744143058L;
	
	private String templateId;		// 템플릿ID
	private String templateNm;		// 템플릿명
	private String mgmtOrgnId;		// 관리부서ID
	private String mgmtOrgnNm;		// 관리부서명
	private String workType;		// 업무구분코드
	private String workTypeNm;		// 업무구분
	private String callback;		// 발신자번호
	private String callbackNm;		// 발신자번호
	private Integer expireHour;		// 만료시간
	private String subject;			// 문자제목
	private String text;			// 문자내용
	private Integer retry;			// 재발송횟수
	private String regstId;			// 등록자ID
	private String regstNm;			// 등록자
	private String regstDttm;		// 등록일시
	private String rvisnId;			// 수정자ID
	private String rvisnNm;			// 수정자
	private String rvisnDttm;		// 수정일시
	private String msgType;			// 메시지 타입
	private String saveType;		// 저장타입(I:등록,U:수정)
	private String kTemplateCode;		// 카카오톡 문자발송 코드
	
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
	public String getMgmtOrgnId() {
		return mgmtOrgnId;
	}
	public void setMgmtOrgnId(String mgmtOrgnId) {
		this.mgmtOrgnId = mgmtOrgnId;
	}
	public String getMgmtOrgnNm() {
		return mgmtOrgnNm;
	}
	public void setMgmtOrgnNm(String mgmtOrgnNm) {
		this.mgmtOrgnNm = mgmtOrgnNm;
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
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public String getCallbackNm() {
		return callbackNm;
	}
	public void setCallbackNm(String callbackNm) {
		this.callbackNm = callbackNm;
	}
	public Integer getExpireHour() {
		return expireHour;
	}
	public void setExpireHour(Integer expireHour) {
		this.expireHour = expireHour;
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
	public Integer getRetry() {
		return retry;
	}
	public void setRetry(Integer retry) {
		this.retry = retry;
	}
	public String getRegstId() {
		return regstId;
	}
	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}
	public String getRegstNm() {
		return regstNm;
	}
	public void setRegstNm(String regstNm) {
		this.regstNm = regstNm;
	}
	public String getRegstDttm() {
		return regstDttm;
	}
	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
	}
	public String getRvisnId() {
		return rvisnId;
	}
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}
	public String getRvisnNm() {
		return rvisnNm;
	}
	public void setRvisnNm(String rvisnNm) {
		this.rvisnNm = rvisnNm;
	}
	public String getRvisnDttm() {
		return rvisnDttm;
	}
	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getSaveType() {
		return saveType;
	}
	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}
	public String getkTemplateCode() {
		return kTemplateCode;
	}
	public void setkTemplateCode(String kTemplateCode) {
		this.kTemplateCode = kTemplateCode;
	}
	
}