package com.ktmmobile.mcp.common.mplatform.vo;

import java.io.Serializable;

public class CodeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dtlCd;//상세코드
    private String dtlCdNm;//코드명
    private String expnsnStrVal1;//확장문자열1
    private String expnsnStrVal2;//확장문자열2
    private String expnsnStrVal3;//확장문자열3
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
