package com.ktmmobile.mcp.phone.dto;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Class Name : PhoneSntyBasDto
 * @Description : 상품속성 기본 테이블
 *
 * @author : ant
 * @Create Date : 2016. 1. 6.
 */
public class PhoneSntyBasDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 상품id */
    private String prodId;

    /** 단말기모델id */
    private String hndsetModelId;

    /** 단말기모델명  */
    private String hndsetModelNm;

    /**속성코드 1 현재 안쓰고있음 */
    private String atribCd1;

    /**속성명1  현재 안쓰고있음  */
    private String atribNm1;

    /** 속성값 코드1(색상) */
    private String atribValCd1;

    /** 속성값명 1(색상) */
    private String atribValNm1;

    /**속성코드 2  현재 안쓰고있음  */
    private String atribCd2;

    /**속성명2   현재 안쓰고있음  */
    private String atribNm2;

    /** 속성값 코드2 (용량)*/
    private String atribValCd2;

    /** 속성값명 2 (용량)*/
    private String atribValNm2;

    /**속성코드 3  현재 안쓰고있음  */
    private String atribCd3;

    /**속성명3  현재 안쓰고있음  */
    private String atribNm3;

    /** 속성값 코드3 */
    private String atribValCd3;

    /** 속성값명 3 */
    private String atribValNm3;

    /** 판매여부 */
    private String saleYn;

    /** 품절여부  */
    private String sdoutYn;

    /** 대표상품여부 */
    private String repProdYn;

    /** 생성자아이디 */
    private String cretId;

    /** 수정자아이디 */
    private String amdId;

    /**생성일시 */
    private Date cretDt;

    /** 수정일시 */
    private Date amdDt;

    /** 대표상품여부 Y/N */
    private String rprsPrdtYn;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getHndsetModelId() {
        return hndsetModelId;
    }

    public void setHndsetModelId(String hndsetModelId) {
        this.hndsetModelId = hndsetModelId;
    }

    public String getHndsetModelNm() {
        return hndsetModelNm;
    }

    public void setHndsetModelNm(String hndsetModelNm) {
        this.hndsetModelNm = hndsetModelNm;
    }

    public String getAtribCd1() {
        return atribCd1;
    }

    public void setAtribCd1(String atribCd1) {
        this.atribCd1 = atribCd1;
    }

    public String getAtribNm1() {
        return atribNm1;
    }

    public void setAtribNm1(String atribNm1) {
        this.atribNm1 = atribNm1;
    }

    public String getAtribValCd1() {
        return atribValCd1;
    }

    public void setAtribValCd1(String atribValCd1) {
        this.atribValCd1 = atribValCd1;
    }

    public String getAtribValNm1() {
        return atribValNm1;
    }

    public void setAtribValNm1(String atribValNm1) {
        this.atribValNm1 = atribValNm1;
    }

    public String getAtribCd2() {
        return atribCd2;
    }

    public void setAtribCd2(String atribCd2) {
        this.atribCd2 = atribCd2;
    }

    public String getAtribNm2() {
        return atribNm2;
    }

    public void setAtribNm2(String atribNm2) {
        this.atribNm2 = atribNm2;
    }

    public String getAtribValCd2() {
        return atribValCd2;
    }

    public void setAtribValCd2(String atribValCd2) {
        this.atribValCd2 = atribValCd2;
    }

    public String getAtribValNm2() {
        return atribValNm2;
    }

    public void setAtribValNm2(String atribValNm2) {
        this.atribValNm2 = atribValNm2;
    }

    public String getAtribCd3() {
        return atribCd3;
    }

    public void setAtribCd3(String atribCd3) {
        this.atribCd3 = atribCd3;
    }

    public String getAtribNm3() {
        return atribNm3;
    }

    public void setAtribNm3(String atribNm3) {
        this.atribNm3 = atribNm3;
    }

    public String getAtribValCd3() {
        return atribValCd3;
    }

    public void setAtribValCd3(String atribValCd3) {
        this.atribValCd3 = atribValCd3;
    }

    public String getAtribValNm3() {
        return atribValNm3;
    }

    public void setAtribValNm3(String atribValNm3) {
        this.atribValNm3 = atribValNm3;
    }

    public String getSaleYn() {
        return saleYn;
    }

    public void setSaleYn(String saleYn) {
        this.saleYn = saleYn;
    }

    public String getSdoutYn() {
        return sdoutYn;
    }

    public void setSdoutYn(String sdoutYn) {
        this.sdoutYn = sdoutYn;
    }

    public String getRepProdYn() {
        return repProdYn;
    }

    public void setRepProdYn(String repProdYn) {
        this.repProdYn = repProdYn;
    }

    public String getCretId() {
        return cretId;
    }

    public void setCretId(String cretId) {
        this.cretId = cretId;
    }

    public String getAmdId() {
        return amdId;
    }

    public void setAmdId(String amdId) {
        this.amdId = amdId;
    }

    public Date getCretDt() {
        return cretDt;
    }

    public void setCretDt(Date cretDt) {
        this.cretDt = cretDt;
    }

    public Date getAmdDt() {
        return amdDt;
    }

    public void setAmdDt(Date amdDt) {
        this.amdDt = amdDt;
    }

    public String getRprsPrdtYn() {
        return rprsPrdtYn;
    }

    public void setRprsPrdtYn(String rprsPrdtYn) {
        this.rprsPrdtYn = rprsPrdtYn;
    }
}
