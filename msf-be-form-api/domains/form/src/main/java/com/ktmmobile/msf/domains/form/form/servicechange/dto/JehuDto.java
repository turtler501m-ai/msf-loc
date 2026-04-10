package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.io.Serializable;

public class JehuDto implements Serializable {

    private static final long serialVersionUID = 1L;


    private String partnerId;
    private String billYm;
    private String rateCd;
    private String contractNum;
    private String fullPayYn;
    private String sendFlag;
    private String payResult;
    private String resultDtlCd;
    private String calPoint;
    private String payPoint;

    public String getPartnerId() {
        return partnerId;
    }
    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
    public String getBillYm() {
        return billYm;
    }
    public void setBillYm(String billYm) {
        this.billYm = billYm;
    }
    public String getRateCd() {
        return rateCd;
    }
    public void setRateCd(String rateCd) {
        this.rateCd = rateCd;
    }
    public String getContractNum() {
        return contractNum;
    }
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }
    public String getFullPayYn() {
        return fullPayYn;
    }
    public void setFullPayYn(String fullPayYn) {
        this.fullPayYn = fullPayYn;
    }
    public String getSendFlag() {
        return sendFlag;
    }
    public void setSendFlag(String sendFlag) {
        this.sendFlag = sendFlag;
    }
    public String getPayResult() {
        return payResult;
    }
    public void setPayResult(String payResult) {
        this.payResult = payResult;
    }
    public String getResultDtlCd() {
        return resultDtlCd;
    }
    public void setResultDtlCd(String resultDtlCd) {
        this.resultDtlCd = resultDtlCd;
    }
    public String getCalPoint() {
        return calPoint;
    }
    public void setCalPoint(String calPoint) {
        this.calPoint = calPoint;
    }
    public String getPayPoint() {
        return payPoint;
    }
    public void setPayPoint(String payPoint) {
        this.payPoint = payPoint;
    }



}
