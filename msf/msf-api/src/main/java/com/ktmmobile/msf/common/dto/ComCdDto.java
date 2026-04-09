package com.ktmmobile.msf.common.dto;

import java.io.Serializable;

public class ComCdDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cdGroupId;
    private String dtlCd;
    private String dtlCdNm;
    private String dtlCdDesc;
    private int indcOdrg;
    private String useYn;
    private String expnsnStrVal1;
    private String expnsnStrVal2;
    private String expnsnStrVal3;

    public String getCdGroupId() {
        return cdGroupId;
    }
    public void setCdGroupId(String cdGroupId) {
        this.cdGroupId = cdGroupId;
    }
    public String getDtlCd() {
        return dtlCd;
    }
    public void setDtlCd(String dtlCd) {
        this.dtlCd = dtlCd;
    }
    public String getDtlCdNm() {
        return dtlCdNm;
    }
    public void setDtlCdNm(String dtlCdNm) {
        this.dtlCdNm = dtlCdNm;
    }
    public String getDtlCdDesc() {
        return dtlCdDesc;
    }
    public void setDtlCdDesc(String dtlCdDesc) {
        this.dtlCdDesc = dtlCdDesc;
    }
    public int getIndcOdrg() {
        return indcOdrg;
    }
    public void setIndcOdrg(int indcOdrg) {
        this.indcOdrg = indcOdrg;
    }
    public String getUseYn() {
        return useYn;
    }
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
    public String getExpnsnStrVal1() {
        return expnsnStrVal1;
    }
    public void setExpnsnStrVal1(String expnsnStrVal1) {
        this.expnsnStrVal1 = expnsnStrVal1;
    }
    public String getExpnsnStrVal2() {
        return expnsnStrVal2;
    }
    public void setExpnsnStrVal2(String expnsnStrVal2) {
        this.expnsnStrVal2 = expnsnStrVal2;
    }
    public String getExpnsnStrVal3() {
        return expnsnStrVal3;
    }
    public void setExpnsnStrVal3(String expnsnStrVal3) {
        this.expnsnStrVal3 = expnsnStrVal3;
    }


}
