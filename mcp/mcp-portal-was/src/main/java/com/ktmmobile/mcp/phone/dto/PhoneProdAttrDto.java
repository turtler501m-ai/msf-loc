package com.ktmmobile.mcp.phone.dto;

import java.sql.Date;
import java.io.Serializable;


/**
 * @Class Name : CodeAttrDto
 * @Description : 핸드폰상품속성코드
 *
 * @author : ant
 * @Create Date : 2016. 1. 6.
 */
public class PhoneProdAttrDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 속성코드 대분류 */
    private String atribCd;

    /** 속성코드 2차코드값  */
    private String atribValCd;

    /** 속성 text 값 */
    private String atribVal;

    /** 사용여부 */
    private String sttusVal;

    /** 생성자 id */
    private String cretId;

    /** 수정자 id */
    private String amdId;

    /** 시작일 */
    private Date cretDt;

    /**  수정일 */
    private Date amdDt;

    /** 정렬순서 */
    private int indcOdrg;

    public String getAtribCd() {
        return atribCd;
    }

    public void setAtribCd(String atribCd) {
        this.atribCd = atribCd;
    }

    public String getAtribValCd() {
        return atribValCd;
    }

    public void setAtribValCd(String atribValCd) {
        this.atribValCd = atribValCd;
    }

    public String getAtribVal() {
        return atribVal;
    }

    public void setAtribVal(String atribVal) {
        this.atribVal = atribVal;
    }

    public String getSttusVal() {
        return sttusVal;
    }

    public void setSttusVal(String sttusVal) {
        this.sttusVal = sttusVal;
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

    public int getIndcOdrg() {
        return indcOdrg;
    }

    public void setIndcOdrg(int indcOdrg) {
        this.indcOdrg = indcOdrg;
    }
}
