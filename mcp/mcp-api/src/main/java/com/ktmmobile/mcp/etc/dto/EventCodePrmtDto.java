package com.ktmmobile.mcp.etc.dto;

import java.io.Serializable;

public class EventCodePrmtDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int ecpSeq;
    private int seq;
    private String prmtId;
    private String prmtNm;
    private String giftText;
    private String mainPrdtId;
    private String giftPrice;
    private String pstngPrmtStartDate;
    private String pstngPrmtEndDate;
    private String pstngPrmtStartHour;
    private String pstngPrmtEndHour;
    private String pstngStartDttm;
    private String pstngEndDttm;
    private String indcOdrg;
    private String cretId;
    private String prdtId;
    private String prdtNm;
    private String prdtUseYn;
    private String prdtWebUrl;
    private String prdtMainYn;

    public int getEcpSeq() {
        return ecpSeq;
    }

    public void setEcpSeq(int ecpSeq) {
        this.ecpSeq = ecpSeq;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getPrmtId() {
        return prmtId;
    }

    public void setPrmtId(String prmtId) {
        this.prmtId = prmtId;
    }

    public String getGiftText() {
        return giftText;
    }

    public String getPrmtNm() {
        return prmtNm;
    }

    public void setPrmtNm(String prmtNm) {
        this.prmtNm = prmtNm;
    }

    public void setGiftText(String giftText) {
        this.giftText = giftText;
    }

    public String getMainPrdtId() {
        return mainPrdtId;
    }

    public void setMainPrdtId(String mainPrdtId) {
        this.mainPrdtId = mainPrdtId;
    }

    public String getGiftPrice() {
        return giftPrice;
    }

    public void setGiftPrice(String giftPrice) {
        this.giftPrice = giftPrice;
    }

    public String getPstngPrmtStartDate() {
        return pstngPrmtStartDate;
    }

    public void setPstngPrmtStartDate(String pstngPrmtStartDate) {
        this.pstngPrmtStartDate = pstngPrmtStartDate;
    }

    public String getPstngPrmtEndDate() {
        return pstngPrmtEndDate;
    }

    public void setPstngPrmtEndDate(String pstngPrmtEndDate) {
        this.pstngPrmtEndDate = pstngPrmtEndDate;
    }

    public String getPstngPrmtStartHour() {
        return pstngPrmtStartHour;
    }

    public void setPstngPrmtStartHour(String pstngPrmtStartHour) {
        this.pstngPrmtStartHour = pstngPrmtStartHour;
    }

    public String getPstngPrmtEndHour() {
        return pstngPrmtEndHour;
    }

    public void setPstngPrmtEndHour(String pstngPrmtEndHour) {
        this.pstngPrmtEndHour = pstngPrmtEndHour;
    }

    public String getPstngStartDttm() {
        return pstngStartDttm;
    }

    public void setPstngStartDttm(String pstngStartDttm) {
        this.pstngStartDttm = pstngStartDttm;
    }

    public String getPstngEndDttm() {
        return pstngEndDttm;
    }

    public void setPstngEndDttm(String pstngEndDttm) {
        this.pstngEndDttm = pstngEndDttm;
    }

    public String getIndcOdrg() {
        return indcOdrg;
    }

    public void setIndcOdrg(String indcOdrg) {
        this.indcOdrg = indcOdrg;
    }

    public String getCretId() {
        return cretId;
    }

    public void setCretId(String cretId) {
        this.cretId = cretId;
    }

    public String getPrdtId() {
        return prdtId;
    }

    public void setPrdtId(String prdtId) {
        this.prdtId = prdtId;
    }

    public String getPrdtNm() {
        return prdtNm;
    }

    public void setPrdtNm(String prdtNm) {
        this.prdtNm = prdtNm;
    }

    public String getPrdtUseYn() {
        return prdtUseYn;
    }

    public void setPrdtUseYn(String prdtUseYn) {
        this.prdtUseYn = prdtUseYn;
    }

    public String getPrdtWebUrl() {
        return prdtWebUrl;
    }

    public void setPrdtWebUrl(String prdtWebUrl) {
        this.prdtWebUrl = prdtWebUrl;
    }

    public String getPrdtMainYn() {
        return prdtMainYn;
    }

    public void setPrdtMainYn(String prdtMainYn) {
        this.prdtMainYn = prdtMainYn;
    }
}
