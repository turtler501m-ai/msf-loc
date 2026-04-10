package com.ktmmobile.mcp.rate.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EventCodePrmtXML {

    private String ecpSeq;               // 이벤트코드 프로모션 시퀀스
    private String evntCd;               // 이벤트코드
    private String evntNm;               // 제목
    private String pstngStartDate;       // 시작일자
    private String pstngEndDate;         // 종료일자
    private String giftUseYn;            // 사은품 영역 사용 여부
    private String recoUseYn;            // 추천인 영역 사용 여부
    private String prmtId;               // 프로모션아이디
    private String giftText;             // 노출문구
    private String mainPrdtId;           // 대표사은품아이디
    private String mainPrdtNm;           // 대표사은품명
    private String mainPrdtImgUrl;       // 대표사은품 이미지
    private int giftPrice;               // 혜택가격
    private String pstngStartDatePrmt;   // 개별 프로모션 시작일시
    private String pstngEndDatePrmt;     // 개별 프로모션 종료일시
    private String exposedText; //이벤트 코드 노출 텍스트

    public String getEcpSeq() {
        return ecpSeq;
    }

    public void setEcpSeq(String ecpSeq) {
        this.ecpSeq = ecpSeq;
    }

    public String getEvntCd() {
        return evntCd;
    }

    public void setEvntCd(String evntCd) {
        this.evntCd = evntCd;
    }

    public String getEvntNm() {
        return evntNm;
    }

    public void setEvntNm(String evntNm) {
        this.evntNm = evntNm;
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

    public String getRecoUseYn() {
        return recoUseYn;
    }

    public void setRecoUseYn(String recoUseYn) {
        this.recoUseYn = recoUseYn;
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

    public void setPstngEndDatePrmt(String pstngEndDatePrmt) {
        this.pstngEndDatePrmt = pstngEndDatePrmt;
    }

    public String getExposedText() {
        return exposedText;
    }

    public void setExposedText(String exposedText) {
        this.exposedText = exposedText;
    }
}
