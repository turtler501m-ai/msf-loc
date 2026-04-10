package com.ktmmobile.mcp.rate.dto;

import java.io.Serializable;

public class RateGiftPrmtDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long seq;                 // 순번
    private String prmtId;            // 프로모션 ID
    private String giftText;          // 노출문구
    private String mainPrdtNm;        // 대표사은품명
    private String mainPrdtImgUrl;    // 대표사은품이미지경로
    private int giftPrice;            // 혜택가격
    private String popupUrl;		  // 팝업 url
    private String popupUrlMo;        // 팝업 url 모바일

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
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

    public void setGiftText(String giftText) {
        this.giftText = giftText;
    }

    public String getMainPrdtNm() {
        return mainPrdtNm;
    }

    public void setMainPrdtNm(String mainPrdtNm) {
        this.mainPrdtNm = mainPrdtNm;
    }

    public String getMainPrdtImgUrl() {
        return mainPrdtImgUrl;
    }

    public void setMainPrdtImgUrl(String mainPrdtImgUrl) {
        this.mainPrdtImgUrl = mainPrdtImgUrl;
    }

    public int getGiftPrice() {
        return giftPrice;
    }

    public void setGiftPrice(int giftPrice) {
        this.giftPrice = giftPrice;
    }

    public String getPopupUrl() {
        return popupUrl;
    }

    public void setPopupUrl(String popupUrl) {
        this.popupUrl = popupUrl;
    }

    public String getPopupUrlMo() {
        return popupUrlMo;
    }

    public void setPopupUrlMo(String popupUrlMo) {
        this.popupUrlMo = popupUrlMo;
    }
}
