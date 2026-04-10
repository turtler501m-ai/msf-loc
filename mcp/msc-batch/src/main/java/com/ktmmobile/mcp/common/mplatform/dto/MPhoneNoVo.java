package com.ktmmobile.mcp.common.mplatform.dto;

import java.io.Serializable;

public class MPhoneNoVo implements Serializable{

    private static final long serialVersionUID = 1L;

    private String tlphNoStatCd;            // 전화번호 상태코드
    private String asgnAgncId;              // 할당 대리점 ID
    private String tlphNoOwnCmncCmpnCd;     // 전화번호소유 통신회사 코드
    private String openSvcIndCd;            // 개통서비스구분코드
    private String encdTlphNo;              // 암호화전화번호
    private String tlphNo;                  // 전화번호
    private String fvrtnoAqcsPsblYn;        // 선호번호획득가능여부
    private String rsrvCustNo;              // 고객번호
    private String statMntnEndPrrnDate;     // 상태유지 종료 예정일
    private String tlphNoChrcCd;            // 전화번호특성코드
    private String tlphNoStatChngDt;        // 전화번호 상태변경일
    private String tlphNoUseCd;             // 번호사용용도코드
    private String tlphNoUseMntCd;          // 번호사용상세사유코드
    private String resNo;                   // 예약번호

    public String getTlphNoStatCd() {
        return tlphNoStatCd;
    }

    public void setTlphNoStatCd(String tlphNoStatCd) {
        this.tlphNoStatCd = tlphNoStatCd;
    }

    public String getAsgnAgncId() {
        return asgnAgncId;
    }

    public void setAsgnAgncId(String asgnAgncId) {
        this.asgnAgncId = asgnAgncId;
    }

    public String getTlphNoOwnCmncCmpnCd() {
        return tlphNoOwnCmncCmpnCd;
    }

    public void setTlphNoOwnCmncCmpnCd(String tlphNoOwnCmncCmpnCd) {
        this.tlphNoOwnCmncCmpnCd = tlphNoOwnCmncCmpnCd;
    }

    public String getOpenSvcIndCd() {
        return openSvcIndCd;
    }

    public void setOpenSvcIndCd(String openSvcIndCd) {
        this.openSvcIndCd = openSvcIndCd;
    }

    public String getEncdTlphNo() {
        return encdTlphNo;
    }

    public void setEncdTlphNo(String encdTlphNo) {
        this.encdTlphNo = encdTlphNo;
    }

    public String getTlphNo() {
        return tlphNo;
    }

    public void setTlphNo(String tlphNo) {
        this.tlphNo = tlphNo;
    }

    public String getFvrtnoAqcsPsblYn() {
        return fvrtnoAqcsPsblYn;
    }

    public void setFvrtnoAqcsPsblYn(String fvrtnoAqcsPsblYn) {
        this.fvrtnoAqcsPsblYn = fvrtnoAqcsPsblYn;
    }

    public String getRsrvCustNo() {
        return rsrvCustNo;
    }

    public void setRsrvCustNo(String rsrvCustNo) {
        this.rsrvCustNo = rsrvCustNo;
    }

    public String getStatMntnEndPrrnDate() {
        return statMntnEndPrrnDate;
    }

    public void setStatMntnEndPrrnDate(String statMntnEndPrrnDate) {
        this.statMntnEndPrrnDate = statMntnEndPrrnDate;
    }

    public String getTlphNoChrcCd() {
        return tlphNoChrcCd;
    }

    public void setTlphNoChrcCd(String tlphNoChrcCd) {
        this.tlphNoChrcCd = tlphNoChrcCd;
    }

    public String getTlphNoStatChngDt() {
        return tlphNoStatChngDt;
    }

    public void setTlphNoStatChngDt(String tlphNoStatChngDt) {
        this.tlphNoStatChngDt = tlphNoStatChngDt;
    }

    public String getTlphNoUseCd() {
        return tlphNoUseCd;
    }

    public void setTlphNoUseCd(String tlphNoUseCd) {
        this.tlphNoUseCd = tlphNoUseCd;
    }

    public String getTlphNoUseMntCd() {
        return tlphNoUseMntCd;
    }

    public void setTlphNoUseMntCd(String tlphNoUseMntCd) {
        this.tlphNoUseMntCd = tlphNoUseMntCd;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }
}
