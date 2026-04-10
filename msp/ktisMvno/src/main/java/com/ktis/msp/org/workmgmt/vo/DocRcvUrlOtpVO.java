package com.ktis.msp.org.workmgmt.vo;

import java.io.Serializable;

public class DocRcvUrlOtpVO implements Serializable {
    private static final long serialVersionUID = -6510970033252597452L;

    private String rcvUrlId;
    private int seq;
    private String otp;
    private String status;
    private String issueDt;
    private int failCnt;
    private String lastAuthDt;
    private String userId;

    public String getRcvUrlId() {
        return rcvUrlId;
    }

    public void setRcvUrlId(String rcvUrlId) {
        this.rcvUrlId = rcvUrlId;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIssueDt() {
        return issueDt;
    }

    public void setIssueDt(String issueDt) {
        this.issueDt = issueDt;
    }

    public int getFailCnt() {
        return failCnt;
    }

    public void setFailCnt(int failCnt) {
        this.failCnt = failCnt;
    }

    public String getLastAuthDt() {
        return lastAuthDt;
    }

    public void setLastAuthDt(String lastAuthDt) {
        this.lastAuthDt = lastAuthDt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
