package com.ktmmobile.mcp.event.dto;

public class EventOrderDtlDto {
    private int mstSeq;
    private int eventOrder;
    private int ntcartSeq;
    private String ntcartSubject;
    private String listViewYn;
    private String cretId;
    private String amdId;

    public int getMstSeq() {
        return mstSeq;
    }

    public void setMstSeq(int mstSeq) {
        this.mstSeq = mstSeq;
    }

    public int getEventOrder() {
        return eventOrder;
    }

    public void setEventOrder(int eventOrder) {
        this.eventOrder = eventOrder;
    }

    public int getNtcartSeq() {
        return ntcartSeq;
    }

    public void setNtcartSeq(int ntcartSeq) {
        this.ntcartSeq = ntcartSeq;
    }

    public String getNtcartSubject() {
        return ntcartSubject;
    }

    public void setNtcartSubject(String ntcartSubject) {
        this.ntcartSubject = ntcartSubject;
    }

    public String getListViewYn() {
        return listViewYn;
    }

    public void setListViewYn(String listViewYn) {
        this.listViewYn = listViewYn;
    }

    public String getCretId() {
        return cretId;
    }

    public void setCretId(String cretId) {
        this.cretId = cretId;
    }

    public String getAmdId() {
        return amdId;
    }

    public void setAmdId(String amdId) {
        this.amdId = amdId;
    }
}
