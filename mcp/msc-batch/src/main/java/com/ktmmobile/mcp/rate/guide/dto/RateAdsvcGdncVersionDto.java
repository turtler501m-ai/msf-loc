package com.ktmmobile.mcp.rate.guide.dto;

public class RateAdsvcGdncVersionDto {
    private long seq;
    private String version;
    private String status;
    private String msg;
    private String expireDt;
    private String deleteDt;

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getExpireDt() {
        return expireDt;
    }

    public void setExpireDt(String expireDt) {
        this.expireDt = expireDt;
    }

    public String getDeleteDt() {
        return deleteDt;
    }

    public void setDeleteDt(String deleteDt) {
        this.deleteDt = deleteDt;
    }
}
