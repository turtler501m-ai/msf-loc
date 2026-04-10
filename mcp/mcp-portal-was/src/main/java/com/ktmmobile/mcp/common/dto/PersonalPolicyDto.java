package com.ktmmobile.mcp.common.dto;

import java.sql.Date;
import java.io.Serializable;

public class PersonalPolicyDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int stpltSeq;
    private String stpltCtgCd;
    private String stpltTitle;
    private String status;
    private String personalinfoEditor;
    private String cretId;
    private Date cretDt;
    private String rownum;
    private String amdId;
    private String sessionId;
    private int stpltHit;

    /*검색*/
    private String inputSearch;
    private String showStatus;
    private String searchGroup;

    private String pageNo;
    
    /*유선 무선 구분 코드*/
    private String siteCode;
    
    public String getSiteCode() {
		return siteCode;
	}
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	public int getStpltHit() {
        return stpltHit;
    }
    public void setStpltHit(int stpltHit) {
        this.stpltHit = stpltHit;
    }
    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public String getPageNo() {
        return pageNo;
    }
    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }
    public String getAmdId() {
        return amdId;
    }
    public void setAmdId(String amdId) {
        this.amdId = amdId;
    }
    public String getRownum() {
        return rownum;
    }
    public void setRownum(String rownum) {
        this.rownum = rownum;
    }
    public String getInputSearch() {
        return inputSearch;
    }
    public void setInputSearch(String inputSearch) {
        this.inputSearch = inputSearch;
    }
    public String getShowStatus() {
        return showStatus;
    }
    public void setShowStatus(String showStatus) {
        this.showStatus = showStatus;
    }
    public String getSearchGroup() {
        return searchGroup;
    }
    public void setSearchGroup(String searchGroup) {
        this.searchGroup = searchGroup;
    }
    public int getStpltSeq() {
        return stpltSeq;
    }
    public void setStpltSeq(int stpltSeq) {
        this.stpltSeq = stpltSeq;
    }
    public String getStpltCtgCd() {
        return stpltCtgCd;
    }
    public void setStpltCtgCd(String stpltCtgCd) {
        this.stpltCtgCd = stpltCtgCd;
    }
    public String getStpltTitle() {
        return stpltTitle;
    }
    public void setStpltTitle(String stpltTitle) {
        this.stpltTitle = stpltTitle;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getPersonalinfoEditor() {
        return personalinfoEditor;
    }
    public void setPersonalinfoEditor(String personalinfoEditor) {
        this.personalinfoEditor = personalinfoEditor;
    }
    public String getCretId() {
        return cretId;
    }
    public void setCretId(String cretId) {
        this.cretId = cretId;
    }
    public Date getCretDt() {
        return cretDt;
    }
    public void setCretDt(Date cretDt) {
        this.cretDt = cretDt;
    }

}
