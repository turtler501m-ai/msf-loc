package com.ktmmobile.msf.form.servicechange.dto;

import java.io.Serializable;
import java.util.Date;

public class CancelConsultDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String custReqSeq;          // 해지 상담 일련번호
    private String cstmrName;         // 고객명
    private String contractNum;       // 계약번호
    private String onlineAuthType;    // 본인인증 방법
    private String onlineAuthInfo;    // 본인인증 정보
    private String reqSeq;
    private String resSeq;
    private String cstmrType;
    private String cstmrNativeRrn;
    private String receiveMobileNo;   // 연락받을 번호
    private String cancelMobileNo;    // 해지신청 번호
    private String survey1Cd;         // 설문 1
    private String survey1Text;       // 설문 1 답변
    private String survey2Cd;         // 설문 2
    private String survey2Text;       // 설문 2
    private Integer surveyScore;      // 만족도
    private String surveySuggestion;  // 개선사항
    private String procCd;            // 처리결과
    private String memo;              // 메모
    private String scanId;            // 스캔 아이디
    private String sysRdateDd;        // 등록일
    private String rip;               // 등록아이피
    private String regstId;           // 등록자 ID
    private Date regstDttm;           // 등록일시
    private String rvisnId;           // 수정자 ID
    private Date rvisnDttm;           // 수정일시

    public String getCustReqSeq() {
        return custReqSeq;
    }
    public void setCustReqSeq(String custReqSeq) {
        this.custReqSeq = custReqSeq;
    }
    public String getCstmrName() {
        return cstmrName;
    }
    public void setCstmrName(String cstmrName) {
        this.cstmrName = cstmrName;
    }
    public String getContractNum() {
        return contractNum;
    }
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }
    public String getOnlineAuthType() {
        return onlineAuthType;
    }
    public void setOnlineAuthType(String onlineAuthType) {
        this.onlineAuthType = onlineAuthType;
    }
    public String getOnlineAuthInfo() {
        return onlineAuthInfo;
    }
    public void setOnlineAuthInfo(String onlineAuthInfo) {
        this.onlineAuthInfo = onlineAuthInfo;
    }
    public String getReqSeq() {
        return reqSeq;
    }
    public void setReqSeq(String reqSeq) {
        this.reqSeq = reqSeq;
    }
    public String getResSeq() {
        return resSeq;
    }
    public void setResSeq(String resSeq) {
        this.resSeq = resSeq;
    }
    public String getCstmrType() {
        return cstmrType;
    }
    public void setCstmrType(String cstmrType) {
        this.cstmrType = cstmrType;
    }
    public String getCstmrNativeRrn() {
        return cstmrNativeRrn;
    }
    public void setCstmrNativeRrn(String cstmrNativeRrn) {
        this.cstmrNativeRrn = cstmrNativeRrn;
    }
    public String getReceiveMobileNo() {
        return receiveMobileNo;
    }
    public void setReceiveMobileNo(String receiveMobileNo) {
        this.receiveMobileNo = receiveMobileNo;
    }
    public String getCancelMobileNo() {
        return cancelMobileNo;
    }
    public void setCancelMobileNo(String cancelMobileNo) {
        this.cancelMobileNo = cancelMobileNo;
    }
    public String getSurvey1Cd() {
        return survey1Cd;
    }
    public void setSurvey1Cd(String survey1Cd) {
        this.survey1Cd = survey1Cd;
    }
    public String getSurvey1Text() {
        return survey1Text;
    }
    public void setSurvey1Text(String survey1Text) {
        this.survey1Text = survey1Text;
    }
    public String getSurvey2Cd() {
        return survey2Cd;
    }
    public void setSurvey2Cd(String survey2Cd) {
        this.survey2Cd = survey2Cd;
    }
    public String getSurvey2Text() {
        return survey2Text;
    }
    public void setSurvey2Text(String survey2Text) {
        this.survey2Text = survey2Text;
    }
    public Integer getSurveyScore() {
        return surveyScore;
    }
    public void setSurveyScore(Integer surveyScore) {
        this.surveyScore = surveyScore;
    }
    public String getSurveySuggestion() {
        return surveySuggestion;
    }
    public void setSurveySuggestion(String surveySuggestion) {
        this.surveySuggestion = surveySuggestion;
    }
    public String getProcCd() {
        return procCd;
    }
    public void setProcCd(String procCd) {
        this.procCd = procCd;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getScanId() {
        return scanId;
    }
    public void setScanId(String scanId) {
        this.scanId = scanId;
    }
    public String getSysRdateDd() {
        return sysRdateDd;
    }
    public void setSysRdateDd(String sysRdateDd) {
        this.sysRdateDd = sysRdateDd;
    }
    public String getRip() {
        return rip;
    }
    public void setRip(String rip) {
        this.rip = rip;
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



}