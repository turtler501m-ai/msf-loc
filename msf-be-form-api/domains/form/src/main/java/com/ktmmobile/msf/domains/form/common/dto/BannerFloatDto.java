package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

public class BannerFloatDto implements Serializable{
    private static final long serialVersionUID = -1207742026359034810L;

    private int bannFloatSeq;          		// 플로팅 배너 일련번호
    private int ntcartSeq;					// 이벤트 일련번호
    private String bannFloatNm;         	// 플로팅 배너 이름
    private String useYn;               	// 사용 여부
    private String pstngStartDate;      	// 노출 시작 날짜
    private String pstngEndDate;        	// 노출 종료 날짜
    private String bannFloatPcImg;		// PC용 이미지 파일 경로
    private String bannFloatMoImg;		// 모바일 이미지 파일 경로
    private String bannFloatImgAlt;     	// 이미지 ALT 텍스트
    private String bannFloatPcUrl;      	// PC용 링크 URL
    private String bannFloatMoUrl;      	// 모바일 링크 URL
    private String bannFloatUrlType;    	// 링크 타입 (새창 : N /현재창 : P)

    public int getBannFloatSeq() {
        return bannFloatSeq;
    }
    public void setBannFloatSeq(int bannFloatSeq) {
        this.bannFloatSeq = bannFloatSeq;
    }
    public int getNtcartSeq() {
        return ntcartSeq;
    }
    public void setNtcartSeq(int ntcartSeq) {
        this.ntcartSeq = ntcartSeq;
    }
    public String getBannFloatNm() {
        return bannFloatNm;
    }
    public void setBannFloatNm(String bannFloatNm) {
        this.bannFloatNm = bannFloatNm;
    }
    public String getUseYn() {
        return useYn;
    }
    public void setUseYn(String useYn) {
        this.useYn = useYn;
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
    public String getBannFloatPcImg() {
        return bannFloatPcImg;
    }
    public void setBannFloatPcImgPath(String bannFloatPcImg) {
        this.bannFloatPcImg = bannFloatPcImg;
    }
    public String getBannFloatMoImg() {
        return bannFloatMoImg;
    }
    public void setBannFloatMoImg(String bannFloatMoImg) {
        this.bannFloatMoImg = bannFloatMoImg;
    }
    public String getBannFloatImgAlt() {
        return bannFloatImgAlt;
    }
    public void setBannFloatImgAlt(String bannFloatImgAlt) {
        this.bannFloatImgAlt = bannFloatImgAlt;
    }
    public String getBannFloatPcUrl() {
        return bannFloatPcUrl;
    }
    public void setBannFloatPcUrl(String bannFloatPcUrl) {
        this.bannFloatPcUrl = bannFloatPcUrl;
    }
    public String getBannFloatMoUrl() {
        return bannFloatMoUrl;
    }
    public void setBannFloatMoUrl(String bannFloatMoUrl) {
        this.bannFloatMoUrl = bannFloatMoUrl;
    }
    public String getBannFloatUrlType() {
        return bannFloatUrlType;
    }
    public void setBannFloatUrlType(String bannFloatUrlType) {
        this.bannFloatUrlType = bannFloatUrlType;
    }


}