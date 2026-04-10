package com.ktmmobile.mcp.etc.dto;

import java.io.Serializable;
import java.util.List;

public class GiftPromotionBas implements Serializable{

    private static final long serialVersionUID = 1L;


    List<GiftPromotionDtl> giftPromotionDtlList;

    // 프로모션 코드
    private String prmtId;
    // 프로모션명
    private String prmtNm;

    /*PRMT_TYPE : [D: 기본사은품, C: 선택사은품] */
    private String prmtType ;

    /* 접점 코드 */
    private String orgnId = "1100011741";  //모바일(직영온라인) 기본값 설정

    /* 선택 가능한 숫 */
    private int limitCnt = 0 ; //LIMIT_CNT

    private String onOffType;

    private String rateCd;

    // 구매유형(MM:단말구매, UU:유심단독)
    private String reqBuyType;

    private String agrmTrm;

    private String operType;

    // 프로모션 단말 추가
    private String modelId;

    private String evntCdPrmt;
    private String reqInDay;

    public String getModelId() {
        return modelId;
    }
    public void setModelId(String modelId) {
        this.modelId = modelId;
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

    public String getOrgnId() {
        return orgnId;
    }

    public void setOrgnId(String orgnId) {
        this.orgnId = orgnId;
    }

    public int getLimitCnt() {
        return limitCnt;
    }

    public void setLimitCnt(int limitCnt) {
        this.limitCnt = limitCnt;
    }

    public String getOnOffType() {
        return onOffType;
    }

    public void setOnOffType(String onOffType) {
        this.onOffType = onOffType;
    }

    public String getRateCd() {
        return rateCd;
    }

    public void setRateCd(String rateCd) {
        this.rateCd = rateCd;
    }

    public String getReqBuyType() {
        return reqBuyType;
    }

    public void setReqBuyType(String reqBuyType) {
        this.reqBuyType = reqBuyType;
    }

    public String getAgrmTrm() {
        return agrmTrm;
    }

    public void setAgrmTrm(String agrmTrm) {
        this.agrmTrm = agrmTrm;
    }

    public List<GiftPromotionDtl> getGiftPromotionDtlList() {
        return giftPromotionDtlList;
    }

    public void setGiftPromotionDtlList(List<GiftPromotionDtl> giftPromotionDtlList) {
        this.giftPromotionDtlList = giftPromotionDtlList;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getPrmtType() {
        return prmtType;
    }

    public void setPrmtType(String prmtType) {
        this.prmtType = prmtType;
    }


    public String getEvntCdPrmt() {
        return evntCdPrmt;
    }

    public void setEvntCdPrmt(String evntCdPrmt) {
        this.evntCdPrmt = evntCdPrmt;
    }

    public String getReqInDay() {
        return reqInDay;
    }

    public void setReqInDay(String reqInDay) {
        this.reqInDay = reqInDay;
    }
}
