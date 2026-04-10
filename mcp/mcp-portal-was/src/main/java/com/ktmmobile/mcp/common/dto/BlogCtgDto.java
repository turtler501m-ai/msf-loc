package com.ktmmobile.mcp.common.dto;

import java.io.Serializable;

public class BlogCtgDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int ctgSeq;
    private String ctgNm;
    private String viewYn;
    private String cretId;
    private String amdId;
    private String cretDt;
    private String amdDt;
    private String modIu;
    private int sortKey;

    public int getCtgSeq() {
        return ctgSeq;
    }
    public void setCtgSeq(int ctgSeq) {
        this.ctgSeq = ctgSeq;
    }
    public String getCtgNm() {
        return ctgNm;
    }
    public void setCtgNm(String ctgNm) {
        this.ctgNm = ctgNm;
    }
    public String getViewYn() {
        return viewYn;
    }
    public void setViewYn(String viewYn) {
        this.viewYn = viewYn;
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
    public String getCretDt() {
        return cretDt;
    }
    public void setCretDt(String cretDt) {
        this.cretDt = cretDt;
    }
    public String getAmdDt() {
        return amdDt;
    }
    public void setAmdDt(String amdDt) {
        this.amdDt = amdDt;
    }
    public String getModIu() {
        return modIu;
    }
    public void setModIu(String modIu) {
        this.modIu = modIu;
    }
    public int getSortKey() {
        return sortKey;
    }
    public void setSortKey(int sortKey) {
        this.sortKey = sortKey;
    }


}
