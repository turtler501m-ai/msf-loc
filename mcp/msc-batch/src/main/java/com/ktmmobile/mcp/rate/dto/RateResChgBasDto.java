package com.ktmmobile.mcp.rate.dto;

import java.io.Serializable;

public class RateResChgBasDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String rateResChgSeq;
    private String svcCntrNo;
    private String mobileNo;
    private String userId;
    private String eventCode;
    private String resChgRateCd;
    private String resChgDate;
    private String resChgApyDate;
    private String trtMdlDiv;
    private String globalNo;
    private String batchRsltCd;
    private String cretIp;
    private String cretDt;
    private String befChgRateCd;
    private int befChgRateAmnt;

    private String contractNum;
    private String subStatus;
    private int payMnthChargeAmt;
    private String nowPriceSocCode;
    private String appStartDd;
    private boolean hasRateInfo;

    public RateResChgBasDto() {}

    public RateResChgBasDto(RateResChgBasDto rateResChgBasDto) {
        this.rateResChgSeq = rateResChgBasDto.getRateResChgSeq();
        this.svcCntrNo = rateResChgBasDto.getSvcCntrNo();
        this.mobileNo = rateResChgBasDto.getMobileNo();
        this.userId = rateResChgBasDto.getUserId();
        this.eventCode = rateResChgBasDto.getEventCode();
        this.resChgRateCd = rateResChgBasDto.getResChgRateCd();
        this.resChgDate = rateResChgBasDto.getResChgDate();
        this.resChgApyDate = rateResChgBasDto.getResChgApyDate();
        this.trtMdlDiv = rateResChgBasDto.getTrtMdlDiv();
        this.globalNo = rateResChgBasDto.getGlobalNo();
        this.batchRsltCd = rateResChgBasDto.getBatchRsltCd();
        this.cretIp = rateResChgBasDto.getCretIp();
        this.cretDt = rateResChgBasDto.getCretDt();
        this.befChgRateCd = rateResChgBasDto.getBefChgRateCd();
        this.befChgRateAmnt = rateResChgBasDto.getBefChgRateAmnt();
        this.contractNum = rateResChgBasDto.getContractNum();
        this.subStatus = rateResChgBasDto.getSubStatus();
        this.payMnthChargeAmt = rateResChgBasDto.getPayMnthChargeAmt();
        this.nowPriceSocCode = rateResChgBasDto.getNowPriceSocCode();
        this.appStartDd = rateResChgBasDto.getAppStartDd();
        this.hasRateInfo = rateResChgBasDto.hasRateInfo();
    }

    public String getRateResChgSeq() {
        return rateResChgSeq;
    }

    public void setRateResChgSeq(String rateResChgSeq) {
        this.rateResChgSeq = rateResChgSeq;
    }

    public String getSvcCntrNo() {
        return svcCntrNo;
    }

    public void setSvcCntrNo(String svcCntrNo) {
        this.svcCntrNo = svcCntrNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getResChgRateCd() {
        return resChgRateCd;
    }

    public void setResChgRateCd(String resChgRateCd) {
        this.resChgRateCd = resChgRateCd;
    }

    public String getResChgDate() {
        return resChgDate;
    }

    public void setResChgDate(String resChgDate) {
        this.resChgDate = resChgDate;
    }

    public String getResChgApyDate() {
        return resChgApyDate;
    }

    public void setResChgApyDate(String resChgApyDate) {
        this.resChgApyDate = resChgApyDate;
    }

    public String getTrtMdlDiv() {
        return trtMdlDiv;
    }

    public void setTrtMdlDiv(String trtMdlDiv) {
        this.trtMdlDiv = trtMdlDiv;
    }

    public String getGlobalNo() {
        return globalNo;
    }

    public void setGlobalNo(String globalNo) {
        this.globalNo = globalNo;
    }

    public String getBatchRsltCd() {
        return batchRsltCd;
    }

    public void setBatchRsltCd(String batchRsltCd) {
        this.batchRsltCd = batchRsltCd;
    }

    public String getCretIp() {
        return cretIp;
    }

    public void setCretIp(String cretIp) {
        this.cretIp = cretIp;
    }

    public String getCretDt() {
        return cretDt;
    }

    public void setCretDt(String cretDt) {
        this.cretDt = cretDt;
    }

    public String getBefChgRateCd() {
        return befChgRateCd;
    }

    public void setBefChgRateCd(String befChgRateCd) {
        this.befChgRateCd = befChgRateCd;
    }

    public int getBefChgRateAmnt() {
        return befChgRateAmnt;
    }

    public void setBefChgRateAmnt(int befChgRateAmnt) {
        this.befChgRateAmnt = befChgRateAmnt;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
    }

    public int getPayMnthChargeAmt() {
        return payMnthChargeAmt;
    }

    public void setPayMnthChargeAmt(int payMnthChargeAmt) {
        this.payMnthChargeAmt = payMnthChargeAmt;
    }

    public String getNowPriceSocCode() {
        return nowPriceSocCode;
    }

    public void setNowPriceSocCode(String nowPriceSocCode) {
        this.nowPriceSocCode = nowPriceSocCode;
    }

    public String getAppStartDd() {
        return appStartDd;
    }

    public void setAppStartDd(String appStartDd) {
        this.appStartDd = appStartDd;
    }

    public boolean hasRateInfo() {
        return hasRateInfo;
    }

    public void setHasRateInfo(boolean hasRateInfo) {
        this.hasRateInfo = hasRateInfo;
    }
}
