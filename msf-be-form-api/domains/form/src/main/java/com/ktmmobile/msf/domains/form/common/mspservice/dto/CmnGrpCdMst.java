package com.ktmmobile.msf.domains.form.common.mspservice.dto;

import java.sql.Date;
import java.io.Serializable;


/**
 * @Class Name : CmnGrpCdMst
 * @Description : MSP CMN_GRP_CD_MST 테이블과 대응되는 DTO
 * MSP 코드 테이블정보이다.
 *
 * @author : ant
 * @Create Date : 2016. 1. 25.
 */
public class CmnGrpCdMst implements Serializable {
    private static final long serialVersionUID = 1L;

    private String grpId;

    private String cdVal;

    private String cdDsc;

    private String usgYn;

    private String regstId;

    private Date regstDttm;

    private String rvisnId;

    private Date rvisnDttm;

    private String etc1;

    private String etc2;

    private String etc3;

    private String etc4;

    private String etc5;

    private String etc6;

    public String getGrpId() {
        return grpId;
    }

    public void setGrpId(String grpId) {
        this.grpId = grpId;
    }

    public String getCdVal() {
        return cdVal;
    }

    public void setCdVal(String cdVal) {
        this.cdVal = cdVal;
    }

    public String getCdDsc() {
        return cdDsc;
    }

    public void setCdDsc(String cdDsc) {
        this.cdDsc = cdDsc;
    }

    public String getUsgYn() {
        return usgYn;
    }

    public void setUsgYn(String usgYn) {
        this.usgYn = usgYn;
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

    public String getEtc1() {
        return etc1;
    }

    public void setEtc1(String etc1) {
        this.etc1 = etc1;
    }

    public String getEtc2() {
        return etc2;
    }

    public void setEtc2(String etc2) {
        this.etc2 = etc2;
    }

    public String getEtc3() {
        return etc3;
    }

    public void setEtc3(String etc3) {
        this.etc3 = etc3;
    }

    public String getEtc4() {
        return etc4;
    }

    public void setEtc4(String etc4) {
        this.etc4 = etc4;
    }

    public String getEtc5() {
        return etc5;
    }

    public void setEtc5(String etc5) {
        this.etc5 = etc5;
    }

    public String getEtc6() {
        return etc6;
    }

    public void setEtc6(String etc6) {
        this.etc6 = etc6;
    }
}
