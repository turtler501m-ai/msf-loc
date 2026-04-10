package com.ktis.msp.org.csanalyticmgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

import java.io.Serializable;

public class AcenCnsltHistVO extends BaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String callStrtDt;
    private String callEndDt;
    private String searchCd;
    private String searchVal;
    private String searchTalkType;
    private String searchCnsltId;
    private String searchSrFir;
    private String searchSrSec;
    private String searchSrThi;
    private String searchSrDtl;
    private String strtDt;
    private String endDt;
    private String strtDttm;
    private String endDttm;

    public String getCallStrtDt() {
        return callStrtDt;
    }

    public void setCallStrtDt(String callStrtDt) {
        this.callStrtDt = callStrtDt;
    }

    public String getCallEndDt() {
        return callEndDt;
    }

    public void setCallEndDt(String callEndDt) {
        this.callEndDt = callEndDt;
    }

    public String getSearchCd() {
        return searchCd;
    }

    public void setSearchCd(String searchCd) {
        this.searchCd = searchCd;
    }

    public String getSearchVal() {
        return searchVal;
    }

    public void setSearchVal(String searchVal) {
        this.searchVal = searchVal;
    }

    public String getSearchTalkType() {
        return searchTalkType;
    }

    public void setSearchTalkType(String searchTalkType) {
        this.searchTalkType = searchTalkType;
    }

    public String getSearchCnsltId() {
        return searchCnsltId;
    }

    public void setSearchCnsltId(String searchCnsltId) {
        this.searchCnsltId = searchCnsltId;
    }

    public String getSearchSrFir() {
        return searchSrFir;
    }

    public void setSearchSrFir(String searchSrFir) {
        this.searchSrFir = searchSrFir;
    }

    public String getSearchSrSec() {
        return searchSrSec;
    }

    public void setSearchSrSec(String searchSrSec) {
        this.searchSrSec = searchSrSec;
    }

    public String getSearchSrThi() {
        return searchSrThi;
    }

    public void setSearchSrThi(String searchSrThi) {
        this.searchSrThi = searchSrThi;
    }

    public String getSearchSrDtl() {
        return searchSrDtl;
    }

    public void setSearchSrDtl(String searchSrDtl) {
        this.searchSrDtl = searchSrDtl;
    }

    public String getStrtDt() {
        return strtDt;
    }

    public void setStrtDt(String strtDt) {
        this.strtDt = strtDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getStrtDttm() {
        return strtDttm;
    }

    public void setStrtDttm(String strtDttm) {
        this.strtDttm = strtDttm;
    }

    public String getEndDttm() {
        return endDttm;
    }

    public void setEndDttm(String endDttm) {
        this.endDttm = endDttm;
    }
}
