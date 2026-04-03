package com.ktmmobile.msf.system.faceauth.dto;

import java.io.Serializable;
import java.util.Date;

public class FathSessionDto implements Serializable {
    private static final long serialVersionUID = -6510970033252597452L;

    private String stbznPerdYn;
    private int tryCount;
    private String transacId;
    private String cmpltNtfyDt;
    private String cpntId;
    private String tempResNo;
    private Date fathResltFirstReqAt;
    private String isFs9;

    public FathSessionDto() {
        this.stbznPerdYn = null;
        this.tryCount = 0;
        this.transacId = null;
        this.cmpltNtfyDt = null;
        this.cpntId = null;
        this.tempResNo = null;
        this.isFs9 = null;
        this.fathResltFirstReqAt = null;
    }

    public String getStbznPerdYn() {
        return stbznPerdYn;
    }

    public void setStbznPerdYn(String stbznPerdYn) {
        this.stbznPerdYn = stbznPerdYn;
    }
    
    public int getTryCount() {
        return tryCount;
    }

    public void setTryCount(int tryCount) {
        this.tryCount = tryCount;
    }

    public String getTransacId() {
        return transacId;
    }

    public void setTransacId(String transacId) {
        this.transacId = transacId;
    }

    public String getCmpltNtfyDt() {
        return cmpltNtfyDt;
    }

    public void setCmpltNtfyDt(String cmpltNtfyDt) {
        this.cmpltNtfyDt = cmpltNtfyDt;
    }

    public String getCpntId() {
        return cpntId;
    }

    public void setCpntId(String cpntId) {
        this.cpntId = cpntId;
    }

    public String getTempResNo() {
        return tempResNo;
    }

    public void setTempResNo(String tempResNo) {
        this.tempResNo = tempResNo;
    }

    public Date getFathResltFirstReqAt() {
        return fathResltFirstReqAt;
    }

    public void setFathResltFirstReqAt(Date fathResltFirstReqAt) {
        this.fathResltFirstReqAt = fathResltFirstReqAt;
    }

    public String getIsFs9() {
        return isFs9;
    }

    public void setIsFs9(String isFs9) {
        this.isFs9 = isFs9;
    }
}
