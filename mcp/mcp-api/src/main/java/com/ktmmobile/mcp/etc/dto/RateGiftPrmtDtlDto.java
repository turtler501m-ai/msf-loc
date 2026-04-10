package com.ktmmobile.mcp.etc.dto;

import java.io.Serializable;

public class RateGiftPrmtDtlDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String prmtId;        // 프로모션 ID
    private String prmtNm;        // 프로모션명
    private String strtDt;        // 시작일자
    private String endDt;         // 종료일자
    private String orgnId;        // 주관부서
    private String orgnIdNm;      // 주관부서명
    private String orgnType;      // 채널유형
    private String orgnTypeNm;    // 채널유형명
    private String prmtType;      // 프로모션 유형
    private String prmtTypeNm;    // 프로모션 유형명
    private String prmtText;      // 노출문구
    private String usgYn;         // 사용여부
    private String showYn;        // 노출여부
    private String wirelessYn;	  // 유무선정책(Y 유선, N 무선)

    /* 화면용 */
    private String strtDtFmt;     // 시작일자(화면용)
    private String endDtFmt;      // 종료일자(화면용)

    /* 페이징 파라미터 */
    private int skipResult;       // 스킵 수
    private int maxResult;        // 추출 수

    /* 조회 파라미터 */
    private String srchStrtDt;    // 시작일
    private String srchEndDt;     // 종료일
    private String srchOrgnId;    // 주관부서
    private String srchPrmtType;  // 프로모션 유형
    private String srchGbn;       // 검색구분
    private String srchName;      // 검색어
    private String srchUseYn;     // 사용여부
    private String srchShowYn;    // 노출여부

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

    public String getStrtDt() {
        return strtDt;
    }

    public void setStrtDt(String strtDt) {
        this.strtDt = strtDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getOrgnId() {
        return orgnId;
    }

    public void setOrgnId(String orgnId) {
        this.orgnId = orgnId;
    }

    public String getOrgnIdNm() {
        return orgnIdNm;
    }

    public void setOrgnIdNm(String orgnIdNm) {
        this.orgnIdNm = orgnIdNm;
    }

    public String getOrgnType() {
        return orgnType;
    }

    public void setOrgnType(String orgnType) {
        this.orgnType = orgnType;
    }

    public String getOrgnTypeNm() {
        return orgnTypeNm;
    }

    public void setOrgnTypeNm(String orgnTypeNm) {
        this.orgnTypeNm = orgnTypeNm;
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

    public String getPrmtText() {
        return prmtText;
    }

    public void setPrmtText(String prmtText) {
        this.prmtText = prmtText;
    }

    public String getUsgYn() {
        return usgYn;
    }

    public void setUsgYn(String usgYn) {
        this.usgYn = usgYn;
    }

    public String getShowYn() {
        return showYn;
    }

    public void setShowYn(String showYn) {
        this.showYn = showYn;
    }

    public String getStrtDtFmt() {
        return strtDtFmt;
    }

    public void setStrtDtFmt(String strtDtFmt) {
        this.strtDtFmt = strtDtFmt;
    }

    public String getEndDtFmt() {
        return endDtFmt;
    }

    public void setEndDtFmt(String endDtFmt) {
        this.endDtFmt = endDtFmt;
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

    public String getSrchStrtDt() {
        return srchStrtDt;
    }

    public void setSrchStrtDt(String srchStrtDt) {
        this.srchStrtDt = srchStrtDt;
    }

    public String getSrchEndDt() {
        return srchEndDt;
    }

    public void setSrchEndDt(String srchEndDt) {
        this.srchEndDt = srchEndDt;
    }

    public String getSrchOrgnId() {
        return srchOrgnId;
    }

    public void setSrchOrgnId(String srchOrgnId) {
        this.srchOrgnId = srchOrgnId;
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

    public String getSrchUseYn() {
        return srchUseYn;
    }

    public void setSrchUseYn(String srchUseYn) {
        this.srchUseYn = srchUseYn;
    }

    public String getSrchShowYn() {
        return srchShowYn;
    }

    public void setSrchShowYn(String srchShowYn) {
        this.srchShowYn = srchShowYn;
    }

    public String getWirelessYn() {
        return wirelessYn;
    }

    public void setWirelessYn(String wirelessYn) {
        this.wirelessYn = wirelessYn;
    }

}
