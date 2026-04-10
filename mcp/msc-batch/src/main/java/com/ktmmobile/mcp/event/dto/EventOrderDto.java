package com.ktmmobile.mcp.event.dto;

import java.util.List;

public class EventOrderDto {
    private int mstSeq;
    private String status;
    private String procYn;
    private String procType;
    private String orderDttm;
    private String orderDateTime;
    private String orderDt;
    private String cretId;
    private String cretDttm;
    private String cretDt;
    private String amdId;
    private String amdDttm;

    public int getMstSeq() {
        return mstSeq;
    }

    public void setMstSeq(int mstSeq) {
        this.mstSeq = mstSeq;
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

    public String getProcType() {
        return procType;
    }

    public void setProcType(String procType) {
        this.procType = procType;
    }

    public String getOrderDttm() {
        return orderDttm;
    }

    public void setOrderDttm(String orderDttm) {
        this.orderDttm = orderDttm;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getOrderDt() {
        return orderDt;
    }

    public void setOrderDt(String orderDt) {
        this.orderDt = orderDt;
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
