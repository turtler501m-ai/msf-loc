package com.ktmmobile.mcp.mmobile.dto;

import java.util.Date;
import java.io.Serializable;

public class NewsDataLinkDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int linkSeq;
    private int newsDatSeq;
    private String linkNm;
    private String linkUrlAdr;
    private String cretId;
    private String amdId;
    private Date cretDt;
    private Date amdDt;

    public int getLinkSeq() {
        return linkSeq;
    }
    public void setLinkSeq(int linkSeq) {
        this.linkSeq = linkSeq;
    }
    public int getNewsDatSeq() {
        return newsDatSeq;
    }
    public void setNewsDatSeq(int newsDatSeq) {
        this.newsDatSeq = newsDatSeq;
    }
    public String getLinkNm() {
        return linkNm;
    }
    public void setLinkNm(String linkNm) {
        this.linkNm = linkNm;
    }
    public String getLinkUrlAdr() {
        return linkUrlAdr;
    }
    public void setLinkUrlAdr(String linkUrlAdr) {
        this.linkUrlAdr = linkUrlAdr;
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
    public Date getCretDt() {
        return cretDt;
    }
    public void setCretDt(Date cretDt) {
        this.cretDt = cretDt;
    }
    public Date getAmdDt() {
        return amdDt;
    }
    public void setAmdDt(Date amdDt) {
        this.amdDt = amdDt;
    }

}
