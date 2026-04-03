package com.ktmmobile.msf.form.servicechange.dto;

import java.io.Serializable;

public class McpRetvRststnDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String svcCntrNo;//NCN
    private String connDate;//접속날짜
    private int tmscnt;//접속제한횟수
    private String code;//서비스코드
    private String amdDt;//수정일자

    public String getSvcCntrNo() {
        return svcCntrNo;
    }
    public void setSvcCntrNo(String svcCntrNo) {
        this.svcCntrNo = svcCntrNo;
    }
    public String getConnDate() {
        return connDate;
    }
    public void setConnDate(String connDate) {
        this.connDate = connDate;
    }
    public int getTmscnt() {
        return tmscnt;
    }
    public void setTmscnt(int tmscnt) {
        this.tmscnt = tmscnt;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getAmdDt() {
        return amdDt;
    }
    public void setAmdDt(String amdDt) {
        this.amdDt = amdDt;
    }


}