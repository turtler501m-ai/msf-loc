package com.ktmmobile.mcp.rate.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name="item")
@XmlType(propOrder = {"seq", "prmtId", "pstngStartDate", "pstngEndDate", "giftText", "mainPrdtId", "mainPrdtNm", "mainPrdtImgUrl", "giftPrice",
                                            "pstngStartDateSec", "pstngEndDateSec", "giftTextSec", "mainPrdtIdSec", "mainPrdtNmSec", "mainPrdtImgUrlSec",
                                            "giftPriceSec", "wirelessYn", "popupUrl","popupUrlMo","listViewYn","listViewYnSec"})
public class RateGiftPrmtXML {

    private long seq;                 // 순번
    private String prmtId;            // 프로모션 ID
    private String pstngStartDate;    // 혜택요약 노출시작일(A)
    private String pstngEndDate;      // 혜택요약 노출종료일(A)
    private String giftText;          // 노출문구(A)
    private String mainPrdtId;        // 대표사은품아이디(A)
    private String mainPrdtNm;        // 대표사은품명(A)
    private String mainPrdtImgUrl;    // 대표사은품이미지경로(A)
    private int giftPrice;            // 혜택가격(A)
    private String pstngStartDateSec; // 혜택요약 노출시작일(B)
    private String pstngEndDateSec;   // 혜택요약 노출종료일(B)
    private String giftTextSec;       // 노출문구(B)
    private String mainPrdtIdSec;     // 대표사은품아이디(B)
    private String mainPrdtNmSec;     // 대표사은품명(B)
    private String mainPrdtImgUrlSec; // 대표사은품이미지경로(B)
    private int giftPriceSec;         // 혜택가격(B)
    private String wirelessYn;		  // 유무선정책(N 유선, Y 무선)
    private String popupUrl;          // 팝업 url
    private String popupUrlMo;        // 팝업 url 모바일
    private String listViewYn;  //가입 조건 노출 여부(A)
    private String listViewYnSec;  //가입 조건 노출 여부(B)



    public long getSeq() {
        return seq;
    }

    @XmlElement
    public void setSeq(long seq) {
        this.seq = seq;
    }

    public String getPrmtId() {
        return prmtId;
    }

    @XmlElement
    public void setPrmtId(String prmtId) {
        this.prmtId = prmtId;
    }

    public String getPstngStartDate() {
        return pstngStartDate;
    }

    @XmlElement
    public void setPstngStartDate(String pstngStartDate) {
        this.pstngStartDate = pstngStartDate;
    }

    public String getPstngEndDate() {
        return pstngEndDate;
    }

    @XmlElement
    public void setPstngEndDate(String pstngEndDate) {
        this.pstngEndDate = pstngEndDate;
    }

    public String getGiftText() {
        return giftText;
    }

    @XmlElement
    public void setGiftText(String giftText) {
        this.giftText = giftText;
    }

    public String getMainPrdtId() {
        return mainPrdtId;
    }

    @XmlElement
    public void setMainPrdtId(String mainPrdtId) {
        this.mainPrdtId = mainPrdtId;
    }

    public String getMainPrdtNm() {
        return mainPrdtNm;
    }

    @XmlElement
    public void setMainPrdtNm(String mainPrdtNm) {
        this.mainPrdtNm = mainPrdtNm;
    }

    public String getMainPrdtImgUrl() {
        return mainPrdtImgUrl;
    }

    @XmlElement
    public void setMainPrdtImgUrl(String mainPrdtImgUrl) {
        this.mainPrdtImgUrl = mainPrdtImgUrl;
    }

    public int getGiftPrice() {
        return giftPrice;
    }

    @XmlElement
    public void setGiftPrice(int giftPrice) {
        this.giftPrice = giftPrice;
    }

    public String getPstngStartDateSec() {
        return pstngStartDateSec;
    }

    @XmlElement
    public void setPstngStartDateSec(String pstngStartDateSec) {
        this.pstngStartDateSec = pstngStartDateSec;
    }

    public String getPstngEndDateSec() {
        return pstngEndDateSec;
    }

    @XmlElement
    public void setPstngEndDateSec(String pstngEndDateSec) {
        this.pstngEndDateSec = pstngEndDateSec;
    }

    public String getGiftTextSec() {
        return giftTextSec;
    }

    @XmlElement
    public void setGiftTextSec(String giftTextSec) {
        this.giftTextSec = giftTextSec;
    }

    public String getMainPrdtIdSec() {
        return mainPrdtIdSec;
    }

    @XmlElement
    public void setMainPrdtIdSec(String mainPrdtIdSec) {
        this.mainPrdtIdSec = mainPrdtIdSec;
    }

    public String getMainPrdtNmSec() {
        return mainPrdtNmSec;
    }

    @XmlElement
    public void setMainPrdtNmSec(String mainPrdtNmSec) {
        this.mainPrdtNmSec = mainPrdtNmSec;
    }

    public String getMainPrdtImgUrlSec() {
        return mainPrdtImgUrlSec;
    }

    @XmlElement
    public void setMainPrdtImgUrlSec(String mainPrdtImgUrlSec) {
        this.mainPrdtImgUrlSec = mainPrdtImgUrlSec;
    }

    public int getGiftPriceSec() {
        return giftPriceSec;
    }

    @XmlElement
    public void setGiftPriceSec(int giftPriceSec) {
        this.giftPriceSec = giftPriceSec;
    }

    public String getWirelessYn() {
        return wirelessYn;
    }

    @XmlElement
    public void setWirelessYn(String wirelessYn) {
        this.wirelessYn = wirelessYn;
    }

    public String getPopupUrl() {
        return popupUrl;
    }

    @XmlElement
    public void setPopupUrl(String popupUrl) {
        this.popupUrl = popupUrl;
    }

    public String getPopupUrlMo() {
        return popupUrlMo;
    }

    @XmlElement
    public void setPopupUrlMo(String popupUrlMo) {
        this.popupUrlMo = popupUrlMo;
    }

    public String getListViewYn() {
        return listViewYn;
    }

    @XmlElement
    public void setListViewYn(String listViewYn) {
        this.listViewYn = listViewYn;
    }

    public String getListViewYnSec() {
        return listViewYnSec;
    }

    @XmlElement
    public void setListViewYnSec(String listViewYnSec) {
        this.listViewYnSec = listViewYnSec;
    }



}
