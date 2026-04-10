package com.ktmmobile.mcp.rate.guide.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name="item")
@XmlType(propOrder = {"ecpSeq", "evntCd", "evntNm", "pstngStartDate", "pstngEndDate", "giftUseYn", "recoUseYn", "prmtId", "giftText", "mainPrdtId", "mainPrdtNm",
        "mainPrdtImgUrl", "giftPrice", "pstngStartDatePrmt", "pstngEndDatePrmt"})
public class RateEventCodePrmtXML {

    private int ecpSeq;                    // 이벤트코드순번
    private String evntCd;                  // 이벤트코드
    private String pstngStartDate;          // 혜택요약 노출시작일
    private String pstngEndDate;            // 혜택요약 노출종료일
    private String giftUseYn;               // 사은품,추천인 영역 사용여부
    private String prmtId;                  // 프로모션 ID
    private String giftText;                // 노출문구
    private String mainPrdtId;              // 대표사은품아이디
    private String mainPrdtNm;              // 대표사은품명
    private String mainPrdtImgUrl;          // 대표사은품이미지경로
    private int giftPrice;                  // 혜택가격
    private String pstngStartDatePrmt;      // 혜택요약 노출시작일
    private String pstngEndDatePrmt;        // 혜택요약 노출종료일
    private String evntNm;
    private String recoUseYn;

    public int getEcpSeq() {
        return ecpSeq;
    }

    public void setEcpSeq(int ecpSeq) {
        this.ecpSeq = ecpSeq;
    }

    public String getEvntCd() {
        return evntCd;
    }

    public void setEvntCd(String evntCd) {
        this.evntCd = evntCd;
    }

    public String getPstngStartDate() {
        return pstngStartDate;
    }

    public void setPstngStartDate(String pstngStartDate) {
        this.pstngStartDate = pstngStartDate;
    }

    public String getPstngEndDate() {
        return pstngEndDate;
    }

    public void setPstngEndDate(String pstngEndDate) {
        this.pstngEndDate = pstngEndDate;
    }

    public String getGiftUseYn() {
        return giftUseYn;
    }

    public void setGiftUseYn(String giftUseYn) {
        this.giftUseYn = giftUseYn;
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

    public String getMainPrdtId() {
        return mainPrdtId;
    }

    public void setMainPrdtId(String mainPrdtId) {
        this.mainPrdtId = mainPrdtId;
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

    public String getPstngStartDatePrmt() {
        return pstngStartDatePrmt;
    }

    public void setPstngStartDatePrmt(String pstngStartDatePrmt) {
        this.pstngStartDatePrmt = pstngStartDatePrmt;
    }

    public String getPstngEndDatePrmt() {
        return pstngEndDatePrmt;
    }

    public void setPstngEndDatePrmt(String pstngEndDatePrmt) { this.pstngEndDatePrmt = pstngEndDatePrmt; }

    public String getEvntNm() { return evntNm; }

    public void setEvntNm(String evntNm) { this.evntNm = evntNm; }

    public String getRecoUseYn() { return recoUseYn; }

    public void setRecoUseYn(String recoUseYn) { this.recoUseYn = recoUseYn; }
}
