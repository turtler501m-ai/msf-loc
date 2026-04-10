package com.ktmmobile.mcp.etc.dto;

import java.io.Serializable;

public class RateGiftPrmtDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long seq;                     // 순번
    private String prmtId;                // 프로모션 ID
    private String prmtNm;                // 프로모션명
    private String prmtType;              // 프로모션 유형
    private String prmtTypeNm;            // 프로모션 유형명
    private int indcOdrg;                 // 노출순서
    private String statVal;               // 사용여부
    private String pstngStartDate;        // 혜택요약 노출시작일 (A)
    private String pstngEndDate;          // 혜택요약 노출종료일 (A)
    private String giftText;              // 노출문구(A)
    private String mainPrdtId;            // 대표사은품아이디(A)
    private String mainPrdtNm;            // 대표사은품명(A)
    private String mainPrdtImgUrl;        // 대표사은품이미지경로(A)
    private int giftPrice;                // 혜택가격(A)
    private String pstngStartDateSec;     // 혜택요약 노출시작일 (B)
    private String pstngEndDateSec;       // 혜택요약 노출종료일 (B)
    private String giftTextSec;           // 노출문구(B)
    private String mainPrdtIdSec;         // 대표사은품아이디(B)
    private String mainPrdtNmSec;         // 대표사은품명(B)
    private String mainPrdtImgUrlSec;     // 대표사은품이미지경로(B)
    private int giftPriceSec;             // 혜택가격(B)
    private String wirelessYn;			  // 유무선정책(Y 유선, N 무선)
    private String popupUrl;			  // 팝업 url(PC)
    private String popupUrlMo;            // 팝업 url(MO)
    private String listViewYn;  //가입 조건 노출 여부(A)
    private String listViewYnSec;  //가입 조건 노출 여부(B)

    /* 페이징 파라미터 */
    private int skipResult;       // 스킵 수
    private int maxResult;        // 추출 수

    /* 조회 파라미터 */
    private String srchPrmtType;  // 프로모션 유형
    private String srchGbn;       // 검색구분
    private String srchName;      // 검색어

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

    public String getPrmtNm() {
        return prmtNm;
    }

    public void setPrmtNm(String prmtNm) {
        this.prmtNm = prmtNm;
    }

    public String getPrmtType() {
        return prmtType;
    }

    public void setPrmtType(String prmtType) {
        this.prmtType = prmtType;
    }

    public String getPrmtTypeNm() {
        return prmtTypeNm;
    }

    public void setPrmtTypeNm(String prmtTypeNm) {
        this.prmtTypeNm = prmtTypeNm;
    }

    public int getIndcOdrg() {
        return indcOdrg;
    }

    public void setIndcOdrg(int indcOdrg) {
        this.indcOdrg = indcOdrg;
    }

    public String getStatVal() {
        return statVal;
    }

    public void setStatVal(String statVal) {
        this.statVal = statVal;
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

    public String getPstngStartDateSec() {
        return pstngStartDateSec;
    }

    public void setPstngStartDateSec(String pstngStartDateSec) {
        this.pstngStartDateSec = pstngStartDateSec;
    }

    public String getPstngEndDateSec() {
        return pstngEndDateSec;
    }

    public void setPstngEndDateSec(String pstngEndDateSec) {
        this.pstngEndDateSec = pstngEndDateSec;
    }

    public String getGiftTextSec() {
        return giftTextSec;
    }

    public void setGiftTextSec(String giftTextSec) {
        this.giftTextSec = giftTextSec;
    }

    public String getMainPrdtIdSec() {
        return mainPrdtIdSec;
    }

    public void setMainPrdtIdSec(String mainPrdtIdSec) {
        this.mainPrdtIdSec = mainPrdtIdSec;
    }

    public String getMainPrdtNmSec() {
        return mainPrdtNmSec;
    }

    public void setMainPrdtNmSec(String mainPrdtNmSec) {
        this.mainPrdtNmSec = mainPrdtNmSec;
    }

    public String getMainPrdtImgUrlSec() {
        return mainPrdtImgUrlSec;
    }

    public void setMainPrdtImgUrlSec(String mainPrdtImgUrlSec) {
        this.mainPrdtImgUrlSec = mainPrdtImgUrlSec;
    }

    public int getGiftPriceSec() {
        return giftPriceSec;
    }

    public void setGiftPriceSec(int giftPriceSec) {
        this.giftPriceSec = giftPriceSec;
    }

    public int getSkipResult() {
        return skipResult;
    }

    public void setSkipResult(int skipResult) {
        this.skipResult = skipResult;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public String getSrchPrmtType() {
        return srchPrmtType;
    }

    public void setSrchPrmtType(String srchPrmtType) {
        this.srchPrmtType = srchPrmtType;
    }

    public String getSrchGbn() {
        return srchGbn;
    }

    public void setSrchGbn(String srchGbn) {
        this.srchGbn = srchGbn;
    }

    public String getSrchName() {
        return srchName;
    }

    public void setSrchName(String srchName) {
        this.srchName = srchName;
    }

    public String getWirelessYn() {
        return wirelessYn;
    }

    public void setWirelessYn(String wirelessYn) {
        this.wirelessYn = wirelessYn;
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

    public String getListViewYn() {
        return listViewYn;
    }

    public void setListViewYn(String listViewYn) {
        this.listViewYn = listViewYn;
    }

    public String getListViewYnSec() {
        return listViewYnSec;
    }

    public void setListViewYnSec(String listViewYnSec) {
        this.listViewYnSec = listViewYnSec;
    }
}
