package com.ktis.msp.voc.abuse.vo;

import com.ktis.msp.base.mvc.BaseVo;
import com.ktis.msp.gift.prmtmgmt.vo.GiftPrmtMgmtSubVO;

import java.util.List;

public class AbuseVO extends BaseVo {

    private String searchStartDt;
    private String searchEndDt;
    private String pSearchGbn;
    private String pSearchName;

    private String contractNum;
    private List<String> newImeiList;
    private List<String> orgImeiList;
    private String openYn;
    private String reason;
    private String reasonDesc;
    private String imei;

    private String mstSeq;

    // 페이징
    public int TOTAL_COUNT;
    public String ROW_NUM;
    public String LINENUM;

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

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public List<String> getNewImeiList() {
        return newImeiList;
    }

    public void setNewImeiList(List<String> newImeiList) {
        this.newImeiList = newImeiList;
    }

    public List<String> getOrgImeiList() {
        return orgImeiList;
    }

    public void setOrgImeiList(List<String> orgImeiList) {
        this.orgImeiList = orgImeiList;
    }

    public String getOpenYn() {
        return openYn;
    }

    public void setOpenYn(String openYn) {
        this.openYn = openYn;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReasonDesc() {
        return reasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        this.reasonDesc = reasonDesc;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMstSeq() {
        return mstSeq;
    }

    public void setMstSeq(String mstSeq) {
        this.mstSeq = mstSeq;
    }

    public int getTOTAL_COUNT() {
        return TOTAL_COUNT;
    }

    public void setTOTAL_COUNT(int TOTAL_COUNT) {
        this.TOTAL_COUNT = TOTAL_COUNT;
    }

    public String getROW_NUM() {
        return ROW_NUM;
    }

    public void setROW_NUM(String ROW_NUM) {
        this.ROW_NUM = ROW_NUM;
    }

    public String getLINENUM() {
        return LINENUM;
    }

    public void setLINENUM(String LINENUM) {
        this.LINENUM = LINENUM;
    }
}
