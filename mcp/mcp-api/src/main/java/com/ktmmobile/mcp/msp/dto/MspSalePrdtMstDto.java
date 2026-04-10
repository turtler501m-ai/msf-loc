package com.ktmmobile.mcp.msp.dto;

import java.math.BigInteger;
import java.io.Serializable;
import java.sql.Date;


/**
 * @Class Name : MspSalePrdtMstDto
 * @Description : MSP 의 MSP_SALE_PRD_MST 테이블과 대응된다.
 * (상품,정책) 별 신규수수료,MNP수수료,기변수수료 등의 정보를 가지고있다.
 * @author : ant
 * @Create Date : 2016. 1. 12.
 */
public class MspSalePrdtMstDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /** msp 상품상세정보  */
    private CmnIntmMdl cmnIntmMdl;

    /** 판매정책코드  */
    private String salePlcyCd;

    /** 제품id(nrds code) */
    private String prdtId;

    /** 중고여부 */
    private String oldYn;

    /** 신규수수료 */
    private BigInteger newCmsnAmt;

    /** mnp 수수료*/
    private BigInteger mnpCmsnAmt;

    /**기변수수료 */
    private BigInteger hcnCmsnAmt;

    /**등록자id*/
    private String regstId;

    /**등록일시 */
    private Date   regstDttm;

    /**수정자id */
    private String rvisnId;

    /**수정일시 */
    private Date rvisnDttm;

    /** 조직코드 */
    private String orgnId;

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

    public BigInteger getNewCmsnAmt() {
        return newCmsnAmt;
    }

    public void setNewCmsnAmt(BigInteger newCmsnAmt) {
        this.newCmsnAmt = newCmsnAmt;
    }

    public BigInteger getMnpCmsnAmt() {
        return mnpCmsnAmt;
    }

    public void setMnpCmsnAmt(BigInteger mnpCmsnAmt) {
        this.mnpCmsnAmt = mnpCmsnAmt;
    }

    public BigInteger getHcnCmsnAmt() {
        return hcnCmsnAmt;
    }

    public void setHcnCmsnAmt(BigInteger hcnCmsnAmt) {
        this.hcnCmsnAmt = hcnCmsnAmt;
    }

    public String getRegstId() {
        return regstId;
    }

    public void setRegstId(String regstId) {
        this.regstId = regstId;
    }

    public Date getRegstDttm() {
        return regstDttm;
    }

    public void setRegstDttm(Date regstDttm) {
        this.regstDttm = regstDttm;
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

    public CmnIntmMdl getCmnIntmMdl() {
        return cmnIntmMdl;
    }

    public void setCmnIntmMdl(CmnIntmMdl cmnIntmMdl) {
        this.cmnIntmMdl = cmnIntmMdl;
    }

    public String getOrgnId() {
        return orgnId;
    }
    
    public void setOrgnId(String orgnId) {
        this.orgnId = orgnId;
    }
}

