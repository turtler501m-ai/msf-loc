package com.ktmmobile.mcp.acen.dto;

import java.io.Serializable;

public class AcenDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String requestKey;          // 가입신청키
    private String resNo;               // 예약번호
    private String evntGrpCd;           // 업무그룹(해피콜 유형그룹)
    private String evntType;            // 업무유형(해피콜 유형코드)
    private String reqStatus;           // 요청상태(N: 요청전, Y: 요청후, T: 요청대기)
    private String endStatus;           // 종결상태(N: 미종결, Y: 종결, P: 보류)
    private String preCheckYn;          // 사전체크 진행여부
    private String openYn;              // 개통 진행여부
    private String succYn;              // 최종 성공여부
    private String pstate;              // 신청서 상태
    private String requestStateCode;    // 신청서 진행상태
    private String rmk;                 // 비고
    private boolean isAcenCall;         // Acen 연동 호출여부
    private String dlvryType;           // 배송유형
    private String rvisnId;             // 신청정보 수정자

    /* APD 테이블 관련*/
    private String prmtId;              // 평생할인 프로모션Id
    private String enggMnthCnt;         // 약정기간
    private String socCode;             // 요금제코드
    private String onOffType;           // 모집경로
    private String operType;            // 신청유형
    private String reqBuyType;          // 구매유형
    private String cntpntShopId;        // 대리점
    private String evntCd;              // 업무구분
    private String cretId;              // 생성자 ID
    private String reqInDay;            // 신청일자

    public String getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(String requestKey) {
        this.requestKey = requestKey;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public String getEvntGrpCd() {
        return evntGrpCd;
    }

    public void setEvntGrpCd(String evntGrpCd) {
        this.evntGrpCd = evntGrpCd;
    }

    public String getEvntType() {
        return evntType;
    }

    public void setEvntType(String evntType) {
        this.evntType = evntType;
    }

    public String getReqStatus() {
        return reqStatus;
    }

    public void setReqStatus(String reqStatus) {
        this.reqStatus = reqStatus;
    }

    public String getEndStatus() {
        return endStatus;
    }

    public void setEndStatus(String endStatus) {
        this.endStatus = endStatus;
    }

    public String getPreCheckYn() {
        return preCheckYn;
    }

    public void setPreCheckYn(String preCheckYn) {
        this.preCheckYn = preCheckYn;
    }

    public String getOpenYn() {
        return openYn;
    }

    public void setOpenYn(String openYn) {
        this.openYn = openYn;
    }

    public String getSuccYn() {
        return succYn;
    }

    public void setSuccYn(String succYn) {
        this.succYn = succYn;
    }

    public String getPstate() {
        return pstate;
    }

    public void setPstate(String pstate) {
        this.pstate = pstate;
    }

    public String getRequestStateCode() {
        return requestStateCode;
    }

    public void setRequestStateCode(String requestStateCode) {
        this.requestStateCode = requestStateCode;
    }

    public String getRmk() {
        return rmk;
    }

    public void setRmk(String rmk) {
        this.rmk = rmk;
    }

    public boolean isAcenCall() {
        return isAcenCall;
    }

    public void setAcenCall(boolean acenCall) {
        isAcenCall = acenCall;
    }

    public String getDlvryType() {
        return dlvryType;
    }

    public void setDlvryType(String dlvryType) {
        this.dlvryType = dlvryType;
    }

    public String getRvisnId() {
        return rvisnId;
    }

    public void setRvisnId(String rvisnId) {
        this.rvisnId = rvisnId;
    }

    public String getPrmtId() {
        return prmtId;
    }

    public void setPrmtId(String prmtId) {
        this.prmtId = prmtId;
    }

    public String getEnggMnthCnt() {
        return enggMnthCnt;
    }

    public void setEnggMnthCnt(String enggMnthCnt) {
        this.enggMnthCnt = enggMnthCnt;
    }

    public String getSocCode() {
        return socCode;
    }

    public void setSocCode(String socCode) {
        this.socCode = socCode;
    }

    public String getOnOffType() {
        return onOffType;
    }

    public void setOnOffType(String onOffType) {
        this.onOffType = onOffType;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getReqBuyType() {
        return reqBuyType;
    }

    public void setReqBuyType(String reqBuyType) {
        this.reqBuyType = reqBuyType;
    }

    public String getCntpntShopId() {
        return cntpntShopId;
    }

    public void setCntpntShopId(String cntpntShopId) {
        this.cntpntShopId = cntpntShopId;
    }

    public String getEvntCd() {
        return evntCd;
    }

    public void setEvntCd(String evntCd) {
        this.evntCd = evntCd;
    }
    public String getCretId() {
        return cretId;
    }

    public void setCretId(String cretId) {
        this.cretId = cretId;
    }

    public String getReqInDay() {
        return reqInDay;
    }

    public void setReqInDay(String reqInDay) {
        this.reqInDay = reqInDay;
    }

}
