package com.ktmmobile.msf.form.common.dto;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * @Class Name : PhoneMspDto
 * @Description : 핸드폰 상품 정보
 * MSP 에서 가지고 있는 핸드폰 상세 메타정보에 대한 정보이다.
 * MSP쪽의 데이터정보만을 필드로 가지고 있다.
 * @author : ant
 * @Create Date : 2015. 12. 31.
 */
public class PhoneMspDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**조직코드  */
    private String orgnId;

    /** 판매정책코드 */
    private String salePlcyCd;

    /** 제품ID */
    private String prdtId;

    /** 중고여부 */
    private String oldYn;

    /** 신규수수료 */
    private int newCmsnAmt;

    /** MNP수수료 */
    private int mnpCmsnAmt;

    /** 기변수수료 */
    private int hcnCmsnAmt;

    /** 대표제품ID */
    private String rprsPrdtId;

    /** 대표제품 여부  */
    private String rprsYn;

    /** 제품명  (ex. LG 클래스)*/
    private String prdtNm;

    /** 제품코드 (ex. LG-F40K) */
    private String prdtCode;

    /** 제품유형코드 */
    private String prdtTypeCd;

    /** 제품구분코드  (LTE:04, 3G:03)*/
    private String prdtIndCd;

    /** 제품색상코드  */
    private String prdtColrCd;

    /**제조사 ID */
    private String mnfctId;

    /** 제품 출시일자 */
    private String prdtLnchDt;

    /** 제품단종일자  */
    private String prdtDt;

    /** 제품구분코드 label 값  */
    private String prdtIndCdLabel;

    /** 제조사 명  */
    private String mnfctNm;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
    * @Description : 제푼구분코드값의 Label 값을 가져온다.
    * @return
    * @Author : ant
    * @Create Date : 2016. 1. 4.
    */
    public String getPrdtIndCdLabel() {
        if (prdtIndCd == null) {
            prdtIndCdLabel = "";
        } else if (prdtIndCd.equals("02")) {
            prdtIndCdLabel = "ALL";
        } else if (prdtIndCd.equals("03")) {
            prdtIndCdLabel = "3G";
        } else if (prdtIndCd.equals("04")) {
            prdtIndCdLabel = "LTE";
        } else {
            prdtIndCdLabel = "";
        }
        return prdtIndCdLabel;
    }

    public String getOrgnId() {
        return orgnId;
    }

    public void setOrgnId(String orgnId) {
        this.orgnId = orgnId;
    }

    public String getSalePlcyCd() {
        return salePlcyCd;
    }

    public void setSalePlcyCd(String salePlcyCd) {
        this.salePlcyCd = salePlcyCd;
    }

    public String getPrdtId() {
        return prdtId;
    }

    public void setPrdtId(String prdtId) {
        this.prdtId = prdtId;
    }

    public String getOldYn() {
        return oldYn;
    }

    public void setOldYn(String oldYn) {
        this.oldYn = oldYn;
    }

    public int getNewCmsnAmt() {
        return newCmsnAmt;
    }

    public void setNewCmsnAmt(int newCmsnAmt) {
        this.newCmsnAmt = newCmsnAmt;
    }

    public int getMnpCmsnAmt() {
        return mnpCmsnAmt;
    }

    public void setMnpCmsnAmt(int mnpCmsnAmt) {
        this.mnpCmsnAmt = mnpCmsnAmt;
    }

    public int getHcnCmsnAmt() {
        return hcnCmsnAmt;
    }

    public void setHcnCmsnAmt(int hcnCmsnAmt) {
        this.hcnCmsnAmt = hcnCmsnAmt;
    }

    public String getRprsPrdtId() {
        return rprsPrdtId;
    }

    public void setRprsPrdtId(String rprsPrdtId) {
        this.rprsPrdtId = rprsPrdtId;
    }

    public String getRprsYn() {
        return rprsYn;
    }

    public void setRprsYn(String rprsYn) {
        this.rprsYn = rprsYn;
    }

    public String getPrdtNm() {
        return prdtNm;
    }

    public void setPrdtNm(String prdtNm) {
        this.prdtNm = prdtNm;
    }

    public String getPrdtCode() {
        return prdtCode;
    }

    public void setPrdtCode(String prdtCode) {
        this.prdtCode = prdtCode;
    }

    public String getPrdtTypeCd() {
        return prdtTypeCd;
    }

    public void setPrdtTypeCd(String prdtTypeCd) {
        this.prdtTypeCd = prdtTypeCd;
    }

    public String getPrdtIndCd() {
        return prdtIndCd;
    }

    public void setPrdtIndCd(String prdtIndCd) {
        this.prdtIndCd = prdtIndCd;
    }

    public String getPrdtColrCd() {
        return prdtColrCd;
    }

    public void setPrdtColrCd(String prdtColrCd) {
        this.prdtColrCd = prdtColrCd;
    }

    public String getMnfctId() {
        return mnfctId;
    }

    public void setMnfctId(String mnfctId) {
        this.mnfctId = mnfctId;
    }

    public String getPrdtLnchDt() {
        return prdtLnchDt;
    }

    public void setPrdtLnchDt(String prdtLnchDt) {
        this.prdtLnchDt = prdtLnchDt;
    }

    public String getPrdtDt() {
        return prdtDt;
    }

    public void setPrdtDt(String prdtDt) {
        this.prdtDt = prdtDt;
    }

    public String getMnfctNm() {
        return mnfctNm;
    }

    public void setMnfctNm(String mnfctNm) {
        this.mnfctNm = mnfctNm;
    }
}
