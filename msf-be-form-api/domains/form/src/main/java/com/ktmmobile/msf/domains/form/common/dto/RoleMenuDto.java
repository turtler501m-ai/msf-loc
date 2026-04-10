package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

public class RoleMenuDto implements Serializable  {

    private static final long serialVersionUID = 1L;


    private String menuSeq;
    private String menuCode;
    private String menuNm;
    private String groupKey;
    private String prntsKey;
    private String sortKey;
    private String depthKey;
    private String urlAdr;
    private String statVal;
    private String cretId;
    private String amdId;
    private String cretDt;
    private String amdDt;

    public String getMenuSeq() {
        return menuSeq;
    }
    public void setMenuSeq(String menuSeq) {
        this.menuSeq = menuSeq;
    }
    public String getMenuCode() {
        return menuCode;
    }
    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }
    public String getMenuNm() {
        return menuNm;
    }
    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }
    public String getGroupKey() {
        return groupKey;
    }
    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }
    public String getPrntsKey() {
        return prntsKey;
    }
    public void setPrntsKey(String prntsKey) {
        this.prntsKey = prntsKey;
    }
    public String getSortKey() {
        return sortKey;
    }
    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }
    public String getDepthKey() {
        return depthKey;
    }
    public void setDepthKey(String deptKey) {
        this.depthKey = deptKey;
    }
    public String getUrlAdr() {
        return urlAdr;
    }
    public void setUrlAdr(String urlAdr) {
        this.urlAdr = urlAdr;
    }
    public String getStatVal() {
        return statVal;
    }
    public void setStatVal(String statVal) {
        this.statVal = statVal;
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


}
