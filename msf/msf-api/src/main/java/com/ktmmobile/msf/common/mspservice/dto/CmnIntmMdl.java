package com.ktmmobile.msf.common.mspservice.dto;

import java.sql.Date;
import java.io.Serializable;


/**
 * @Class Name : CmnIntmMdl
 * @Description : msp 상품상세정보 (msp cmn_intm_mdl 테이블과 대응된다)
 *
 * @author : ant
 * @Create Date : 2016. 2. 11.
 */
public class CmnIntmMdl implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 제품id */
    private String prdtId;

    /** 대표 id */
    private String rprsPrdtId;

    /** 대표여부 */
    private String rprsYn;

    /** 제품명 */
    private String prdtNm;

    /** 제품코드 */
    private String prdtCode;

    /** 제품유형코드*/
    private String prdtTypeCd;

    /** 제품구분코드*/
    private String prdtIndCd;

    /** 제품색상코드 */
    private String prdtColrCd;

    /** 제조사id*/
    private String mnfctId;

    /** 제품출시일자 */
    private String prdtLnchDt;

    /** 제품단종일자 */
    private String prdtDt;

    /** 등록자 */
    private String regId;

    /** 등록일시 */
    private Date regDttm;

    /** 수정자 */
    private String rvisnId;

    /** 수정일시  */
    private Date rvisnDttm;

    public String getPrdtId() {
        return prdtId;
    }

    public void setPrdtId(String prdtId) {
        this.prdtId = prdtId;
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

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public Date getRegDttm() {
        return regDttm;
    }

    public void setRegDttm(Date regDttm) {
        this.regDttm = regDttm;
    }

    public String getRvisnId() {
        return rvisnId;
    }

    public void setRvisnId(String rvisnId) {
        this.rvisnId = rvisnId;
    }

    public Date getRvisnDttm() {
        return rvisnDttm;
    }

    public void setRvisnDttm(Date rvisnDttm) {
        this.rvisnDttm = rvisnDttm;
    }
}
