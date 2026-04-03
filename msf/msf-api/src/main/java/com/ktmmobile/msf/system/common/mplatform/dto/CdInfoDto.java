package com.ktmmobile.msf.system.common.mplatform.dto;

import java.io.Serializable;

public class CdInfoDto implements Serializable {

    /* 코드*/
    private String cd;

    /* 코드 설명*/
    private String cdDscr;

    private String rfrnVal1;

    private String rfrnVal2;


    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public String getCdDscr() {
        return cdDscr;
    }

    public void setCdDscr(String cdDscr) {
        this.cdDscr = cdDscr;
    }

    public String getRfrnVal1() {
        if (rfrnVal1 == null) {
            return "";
        }
        return rfrnVal1;
    }

    public void setRfrnVal1(String rfrnVal1) {
        this.rfrnVal1 = rfrnVal1;
    }

    public String getRfrnVal2() {
        if (rfrnVal2 == null) {
            return "";
        }
        return rfrnVal2;
    }

    public void setRfrnVal2(String rfrnVal2) {
        this.rfrnVal2 = rfrnVal2;
    }
}
