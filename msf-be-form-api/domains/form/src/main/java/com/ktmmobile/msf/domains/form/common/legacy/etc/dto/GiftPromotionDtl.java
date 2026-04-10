package com.ktmmobile.msf.domains.form.common.legacy.etc.dto;

import java.io.Serializable;

public class GiftPromotionDtl implements Serializable{

    private static final long serialVersionUID = 1L;

    /** 가입신청_키 */
    private long requestKey;

    /** 등록아이피 */
    private String rip;

    // 프로모션 코드
    private String prmtId;

    // MSP_GIFT_PRDT@DL_MSP
    // 제품ID
    private String prdtId;

    // 제품명
    private String prdtNm;
    // 제품상세설명
    private String prdtDesc;
    // 단가
    private String outUnitPric;

    // 저장된이미지파일명
    private String imgFile;
    // 이미지파일명
    private String webUrl;

    private String prmtType ;


    private String cretId ;





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
    public String getPrdtDesc() {
        return prdtDesc;
    }
    public void setPrdtDesc(String prdtDesc) {
        this.prdtDesc = prdtDesc;
    }
    public String getOutUnitPric() {
        return outUnitPric;
    }
    public void setOutUnitPric(String outUnitPric) {
        this.outUnitPric = outUnitPric;
    }
    public String getImgFile() {
        return imgFile;
    }
    public void setImgFile(String imgFile) {
        this.imgFile = imgFile;
    }
    public String getWebUrl() {
        return webUrl;
    }
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
    public String getPrmtId() {
        return prmtId;
    }
    public void setPrmtId(String prmtId) {
        this.prmtId = prmtId;
    }
    public long getRequestKey() {
        return requestKey;
    }
    public void setRequestKey(long requestKey) {
        this.requestKey = requestKey;
    }
    public String getRip() {
        return rip;
    }
    public void setRip(String rip) {
        this.rip = rip;
    }
    public String getPrmtType() {
        return prmtType;
    }
    public void setPrmtType(String prmtType) {
        this.prmtType = prmtType;
    }
    public String getCretId() {
        return cretId;
    }
    public void setCretId(String cretId) {
        this.cretId = cretId;
    }









}
