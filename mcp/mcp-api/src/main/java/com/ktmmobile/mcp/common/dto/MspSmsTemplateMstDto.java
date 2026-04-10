package com.ktmmobile.mcp.common.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @Class Name : MspSmsTemplateMstDto.java
 * @Description : 가입신청_부가서비스 테이블(MSP_SMS_TEMPLATE_MST)
 * 
 */
public class MspSmsTemplateMstDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 제목 */
    private String subject;
    /** 업무구분 */
    private String workType;
    /** 재시도횟수 */
    private int retry;
    /** Template ID */
    private int templateId;
    /** Template 명 */
    private String templateNm;
    /** Template 상세 */
    private String templateDsc;
    /** 관리조직ID */
    private String mgmtOrgnId;
    /** 메시지 타입(1:SMS,) */
    private String msgType;
    /** 발신자번호 */
    private String callback;
    /** Template 내용 */
    private String text;
    /** 등록자ID */
    private String regstId;
    /** 등록일시 */
    private Date regstDttm;
    /** 수정자ID */
    private String rvisnId;
    /** 수정일시 */
    private Date rvisnDttm;
    /** 만료시간(HOUR) */
    private int expireHour;
    /** 카카오 알림톡 템플릿 코드 */
    private String kTemplateCode;
    
    public String getkTemplateCode() {
		return kTemplateCode;
	}
	public void setkTemplateCode(String kTemplateCode) {
		this.kTemplateCode = kTemplateCode;
	}
	public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getWorkType() {
        return workType;
    }
    public void setWorkType(String workType) {
        this.workType = workType;
    }
    public int getRetry() {
        return retry;
    }
    public void setRetry(int retry) {
        this.retry = retry;
    }
    public int getTemplateId() {
        return templateId;
    }
    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }
    public String getTemplateNm() {
        return templateNm;
    }
    public void setTemplateNm(String templateNm) {
        this.templateNm = templateNm;
    }
    public String getTemplateDsc() {
        return templateDsc;
    }
    public void setTemplateDsc(String templateDsc) {
        this.templateDsc = templateDsc;
    }
    public String getMgmtOrgnId() {
        return mgmtOrgnId;
    }
    public void setMgmtOrgnId(String mgmtOrgnId) {
        this.mgmtOrgnId = mgmtOrgnId;
    }
    public String getMsgType() {
        return msgType;
    }
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
    public String getCallback() {
        return callback;
    }
    public void setCallback(String callback) {
        this.callback = callback;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getRegstId() {
        return regstId;
    }
    public void setRegstId(String regstId) {
        this.regstId = regstId;
    }
    public Date getRegstDttm() {
        return regstDttm;
    }
    public void setRegstDttm(Date regstDttm) {
        this.regstDttm = regstDttm;
    }
    public String getRvisnId() {
        return rvisnId;
    }
    public void setRvisnId(String rvisnId) {
        this.rvisnId = rvisnId;
    }
    public Date getRvisnDttm() {
        return rvisnDttm;
    }
    public void setRvisnDttm(Date rvisnDttm) {
        this.rvisnDttm = rvisnDttm;
    }
    public int getExpireHour() {
        return expireHour;
    }
    public void setExpireHour(int expireHour) {
        this.expireHour = expireHour;
    }

}
