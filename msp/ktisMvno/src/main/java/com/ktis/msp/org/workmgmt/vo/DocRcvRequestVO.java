package com.ktis.msp.org.workmgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

import java.util.List;

public class DocRcvRequestVO extends BaseVo {
    private String docRcvId;
    private String resNo;
    private String workTmplId;
    private String smsTemplateId;
    private String mobileNo;
    private String scanId;
    private String expireDt;
    private String viewType;

    private String searchStartDate;
    private String searchEndDate;
    private String rcvYn;
    private String workTmplNm;
    private String searchType;
    private String searchVal;
    private String workId;
    private String urlStatus;
    private String procStatus;

    private List<Long> itemSeqList;
    private String itemId;

    public String getDocRcvId() {
        return docRcvId;
    }

    public void setDocRcvId(String docRcvId) {
        this.docRcvId = docRcvId;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public String getWorkTmplId() {
        return workTmplId;
    }

    public void setWorkTmplId(String workTmplId) {
        this.workTmplId = workTmplId;
    }

    public String getSmsTemplateId() {
        return smsTemplateId;
    }

    public void setSmsTemplateId(String smsTemplateId) {
        this.smsTemplateId = smsTemplateId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getScanId() {
        return scanId;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId;
    }

    public String getExpireDt() {
        return expireDt;
    }

    public void setExpireDt(String expireDt) {
        this.expireDt = expireDt;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getSearchStartDate() {
        return searchStartDate;
    }

    public void setSearchStartDate(String searchStartDate) {
        this.searchStartDate = searchStartDate;
    }

    public String getSearchEndDate() {
        return searchEndDate;
    }

    public void setSearchEndDate(String searchEndDate) {
        this.searchEndDate = searchEndDate;
    }

    public String getRcvYn() {
        return rcvYn;
    }

    public void setRcvYn(String rcvYn) {
        this.rcvYn = rcvYn;
    }

    public String getWorkTmplNm() {
        return workTmplNm;
    }

    public void setWorkTmplNm(String workTmplNm) {
        this.workTmplNm = workTmplNm;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchVal() {
        return searchVal;
    }

    public void setSearchVal(String searchVal) {
        this.searchVal = searchVal;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getUrlStatus() {
        return urlStatus;
    }

    public void setUrlStatus(String urlStatus) {
        this.urlStatus = urlStatus;
    }

    public String getProcStatus() {
        return procStatus;
    }

    public void setProcStatus(String procStatus) {
        this.procStatus = procStatus;
    }

    public List<Long> getItemSeqList() {
        return itemSeqList;
    }

    public void setItemSeqList(List<Long> itemSeqList) {
        this.itemSeqList = itemSeqList;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
