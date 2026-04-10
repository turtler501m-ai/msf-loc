package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import java.io.Serializable;

import com.ktmmobile.msf.domains.form.common.util.StringUtil;


public class MPhoneNoVo implements Serializable  {
    private static final long serialVersionUID = 1L;

    private String asgnAgncId ;
    private String encdTlphNo ;
    private String fvrtnoAqcsPsblYn ;
    private String openSvcIndCd ;
    private String rsrvCustNo ;
    private String statMntnEndPrrnDate ;
    private String tlphNo ;
    private String tlphNoChrcCd ;
    private String tlphNoOwnCmncCmpnCd ;
    private String tlphNoStatCd ;
    private String tlphNoStatChngDt ;
    private String tlphNoUseCd ;
    private String tlphNoUseMntCd ;
    public String getAsgnAgncId() {
        return asgnAgncId;
    }
    public void setAsgnAgncId(String asgnAgncId) {
        this.asgnAgncId = asgnAgncId;
    }
    public String getEncdTlphNo() {
        return encdTlphNo;
    }
    public void setEncdTlphNo(String encdTlphNo) {
        this.encdTlphNo = encdTlphNo;
    }
    public String getFvrtnoAqcsPsblYn() {
        return fvrtnoAqcsPsblYn;
    }
    public void setFvrtnoAqcsPsblYn(String fvrtnoAqcsPsblYn) {
        this.fvrtnoAqcsPsblYn = fvrtnoAqcsPsblYn;
    }
    public String getOpenSvcIndCd() {
        return openSvcIndCd;
    }
    public void setOpenSvcIndCd(String openSvcIndCd) {
        this.openSvcIndCd = openSvcIndCd;
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
    public String getTlphNo() {
        return tlphNo;
    }
    public String getTlphNoView() {
        return StringUtil.getDivPhoneNum(tlphNo);
    }
    public void setTlphNo(String tlphNo) {
        this.tlphNo = tlphNo;
    }
    public String getTlphNoChrcCd() {
        return tlphNoChrcCd;
    }
    public void setTlphNoChrcCd(String tlphNoChrcCd) {
        this.tlphNoChrcCd = tlphNoChrcCd;
    }
    public String getTlphNoOwnCmncCmpnCd() {
        return tlphNoOwnCmncCmpnCd;
    }
    public void setTlphNoOwnCmncCmpnCd(String tlphNoOwnCmncCmpnCd) {
        this.tlphNoOwnCmncCmpnCd = tlphNoOwnCmncCmpnCd;
    }
    public String getTlphNoStatCd() {
        return tlphNoStatCd;
    }
    public void setTlphNoStatCd(String tlphNoStatCd) {
        this.tlphNoStatCd = tlphNoStatCd;
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




}
