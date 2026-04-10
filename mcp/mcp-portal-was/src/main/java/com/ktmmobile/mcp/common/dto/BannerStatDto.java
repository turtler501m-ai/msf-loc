package com.ktmmobile.mcp.common.dto;

import java.io.Serializable;

public class BannerStatDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int bannSeq;
    private String bannNm;
    private String cretDt;
    private int requestStateCodeSum1;
    private int requestStateCodeSum2;
    private int rnum;
    private String searchVal;
    private String selectOption;
    private String reqInDay;

    public int getBannSeq() {
        return bannSeq;
    }
    public void setBannSeq(int bannSeq) {
        this.bannSeq = bannSeq;
    }
    public String getBannNm() {
        return bannNm;
    }
    public void setBannNm(String bannNm) {
        this.bannNm = bannNm;
    }
    public String getCretDt() {
        return cretDt;
    }
    public void setCretDt(String cretDt) {
        this.cretDt = cretDt;
    }
    public int getRequestStateCodeSum1() {
        return requestStateCodeSum1;
    }
    public void setRequestStateCodeSum1(int requestStateCodeSum1) {
        this.requestStateCodeSum1 = requestStateCodeSum1;
    }
    public int getRequestStateCodeSum2() {
        return requestStateCodeSum2;
    }
    public void setRequestStateCodeSum2(int requestStateCodeSum2) {
        this.requestStateCodeSum2 = requestStateCodeSum2;
    }
    public int getRnum() {
        return rnum;
    }
    public void setRnum(int rnum) {
        this.rnum = rnum;
    }
    public String getSearchVal() {
        return searchVal;
    }
    public void setSearchVal(String searchVal) {
        this.searchVal = searchVal;
    }
    public String getSelectOption() {
        return selectOption;
    }
    public void setSelectOption(String selectOption) {
        this.selectOption = selectOption;
    }
    public String getReqInDay() {
        return reqInDay;
    }
    public void setReqInDay(String reqInDay) {
        this.reqInDay = reqInDay;
    }


}
