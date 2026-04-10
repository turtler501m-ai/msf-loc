package com.ktmmobile.mcp.texting.dto;

import java.io.Serializable;

public class FormDtlDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cdGroupId1;
    private String cdGroupId2;
    private String dtlCdNm;
    private String dtlCd;
    private String pstngStartDate;
    private String pstngEndDate;

    public String getCdGroupId1() {
        return cdGroupId1;
    }
    public void setCdGroupId1(String cdGroupId1) {
        this.cdGroupId1 = cdGroupId1;
    }
    public String getCdGroupId2() {
        return cdGroupId2;
    }
    public void setCdGroupId2(String cdGroupId2) {
        this.cdGroupId2 = cdGroupId2;
    }
    public String getDtlCdNm() {
        return dtlCdNm;
    }
    public void setDtlCdNm(String dtlCdNm) {
        this.dtlCdNm = dtlCdNm;
    }
    public String getDtlCd() {
        return dtlCd;
    }
    public void setDtlCd(String dtlCd) {
        this.dtlCd = dtlCd;
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
}
