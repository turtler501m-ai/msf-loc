package com.ktmmobile.mcp.renew.dto;

public class RenewReserveDto {
    private int renewSeq;
    private String renewCd;
    private String renewDt;
    private String trgKey;
    private String status;
    private String procYn;
    private String cretId;
    private String cretDttm;
    private String cretDt;
    private String amdId;
    private String amdDttm;

    public int getRenewSeq() {
        return renewSeq;
    }

    public void setRenewSeq(int renewSeq) {
        this.renewSeq = renewSeq;
    }

    public String getRenewCd() {
        return renewCd;
    }

    public void setRenewCd(String renewCd) {
        this.renewCd = renewCd;
    }

    public String getRenewDt() {
        return renewDt;
    }

    public void setRenewDt(String renewDt) {
        this.renewDt = renewDt;
    }

    public String getTrgKey() {
        return trgKey;
    }

    public void setTrgKey(String trgKey) {
        this.trgKey = trgKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProcYn() {
        return procYn;
    }

    public void setProcYn(String procYn) {
        this.procYn = procYn;
    }

    public String getCretId() {
        return cretId;
    }

    public void setCretId(String cretId) {
        this.cretId = cretId;
    }

    public String getCretDttm() {
        return cretDttm;
    }

    public void setCretDttm(String cretDttm) {
        this.cretDttm = cretDttm;
    }

    public String getCretDt() {
        return cretDt;
    }

    public void setCretDt(String cretDt) {
        this.cretDt = cretDt;
    }

    public String getAmdId() {
        return amdId;
    }

    public void setAmdId(String amdId) {
        this.amdId = amdId;
    }

    public String getAmdDttm() {
        return amdDttm;
    }

    public void setAmdDttm(String amdDttm) {
        this.amdDttm = amdDttm;
    }
}
