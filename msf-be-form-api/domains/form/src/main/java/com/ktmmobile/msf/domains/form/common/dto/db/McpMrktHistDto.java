package com.ktmmobile.msf.domains.form.common.dto.db;

import java.io.Serializable;


public class McpMrktHistDto implements Serializable {
    private String userid;
    private String gubun;
    private String strtDttm;
    private String endDttm;
    private String agrYn;
    private String regstId;
    private String regstDttm;
    private String rvisnId;
    private String rvisnDttm;

    private String newAgrYn;
    private String newStrtDttm;
    private String newEndDttm;
    private String mtkAgrReferer;
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setGubun(String gubun) {
        this.gubun = gubun;
    }

    public String getGubun() {
        return gubun;
    }

    public void setStrtDttm(String strtDttm) {
        this.strtDttm = strtDttm;
    }

    public String getStrtDttm() {
        return strtDttm;
    }

    public void setEndDttm(String endDttm) {
        this.endDttm = endDttm;
    }

    public String getEndDttm() {
        return endDttm;
    }

    public void setAgrYn(String agrYn) {
        this.agrYn = agrYn;
    }

    public String getAgrYn() {
        return agrYn;
    }

    public void setRegstId(String regstId) {
        this.regstId = regstId;
    }

    public String getRegstId() {
        return regstId;
    }

    public void setRegstDttm(String regstDttm) {
        this.regstDttm = regstDttm;
    }

    public String getRegstDttm() {
        return regstDttm;
    }

    public void setRvisnId(String rvisnId) {
        this.rvisnId = rvisnId;
    }

    public String getRvisnId() {
        return rvisnId;
    }

    public void setRvisnDttm(String rvisnDttm) {
        this.rvisnDttm = rvisnDttm;
    }

    public String getRvisnDttm() {
        return rvisnDttm;
    }

    public void setNewAgrYn(String newAgrYn) {
        this.newAgrYn = newAgrYn;
    }

    public String getNewAgrYn() {
        return newAgrYn;
    }

    public void setNewStrtDttm(String newStrtDttm) {
        this.newStrtDttm = newStrtDttm;
    }

    public String getNewStrtDttm() {
        return newStrtDttm;
    }

    public void setNewEndDttm(String newEndDttm) {
        this.newEndDttm = newEndDttm;
    }

    public String getNewEndDttm() {
        return newEndDttm;
    }

    public String getMtkAgrReferer() {
        return mtkAgrReferer;
    }

    public void setMtkAgrReferer(String mtkAgrReferer) {
        this.mtkAgrReferer = mtkAgrReferer;
    }
}
