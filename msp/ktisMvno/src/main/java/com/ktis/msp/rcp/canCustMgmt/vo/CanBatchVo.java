package com.ktis.msp.rcp.canCustMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class CanBatchVo extends BaseVo {

    private String searchStartDt;
    private String searchEndDt;
    private String pSearchGbn;
    private String pSearchName;
    private String procStatus;
    private String fileName;
    private String contractNum;

    public String getSearchStartDt() {
        return searchStartDt;
    }

    public void setSearchStartDt(String searchStartDt) {
        this.searchStartDt = searchStartDt;
    }

    public String getSearchEndDt() {
        return searchEndDt;
    }

    public void setSearchEndDt(String searchEndDt) {
        this.searchEndDt = searchEndDt;
    }

    public String getpSearchGbn() {
        return pSearchGbn;
    }

    public void setpSearchGbn(String pSearchGbn) {
        this.pSearchGbn = pSearchGbn;
    }

    public String getpSearchName() {
        return pSearchName;
    }

    public void setpSearchName(String pSearchName) {
        this.pSearchName = pSearchName;
    }

    public String getProcStatus() {
        return procStatus;
    }

    public void setProcStatus(String procStatus) {
        this.procStatus = procStatus;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }
}
