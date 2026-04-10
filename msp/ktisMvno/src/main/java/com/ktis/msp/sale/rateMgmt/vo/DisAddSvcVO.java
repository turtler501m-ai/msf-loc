package com.ktis.msp.sale.rateMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

import java.util.List;

public class DisAddSvcVO extends BaseVo{
    private String rateCd;
    private String rateNm;
    private String baseAmt;
    private String dupYn;
    private String usgYn;
    private String rowCheck;

    List<DisAddSvcVO> items;

    public String getRateCd() {
        return rateCd;
    }

    public void setRateCd(String rateCd) {
        this.rateCd = rateCd;
    }

    public String getRateNm() {
        return rateNm;
    }

    public void setRateNm(String rateNm) {
        this.rateNm = rateNm;
    }

    public String getBaseAmt() {
        return baseAmt;
    }

    public void setBaseAmt(String baseAmt) {
        this.baseAmt = baseAmt;
    }

    public String getDupYn() {
        return dupYn;
    }

    public void setDupYn(String dupYn) {
        this.dupYn = dupYn;
    }

    public String getUsgYn() {
        return usgYn;
    }

    public void setUsgYn(String usgYn) {
        this.usgYn = usgYn;
    }

    public String getRowCheck() {
        return rowCheck;
    }

    public void setRowCheck(String rowCheck) {
        this.rowCheck = rowCheck;
    }

    public List<DisAddSvcVO> getItems() {
        return items;
    }

    public void setItems(List<DisAddSvcVO> items) {
        this.items = items;
    }
}
