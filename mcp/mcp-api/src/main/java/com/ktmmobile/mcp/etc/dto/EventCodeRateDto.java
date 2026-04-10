package com.ktmmobile.mcp.etc.dto;

import java.io.Serializable;

public class EventCodeRateDto implements Serializable {

    private String selectOpt;
    private String searchNm;

    private int seq;
    private String rateCd;
    private String rateNm;


    public String getSelectOpt() {
        return selectOpt;
    }

    public void setSelectOpt(String selectOpt) {
        this.selectOpt = selectOpt;
    }

    public String getSearchNm() {
        return searchNm;
    }

    public void setSearchNm(String searchNm) {
        this.searchNm = searchNm;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

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
}
