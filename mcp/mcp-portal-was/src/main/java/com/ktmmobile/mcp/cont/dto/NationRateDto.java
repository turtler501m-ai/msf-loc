package com.ktmmobile.mcp.cont.dto;

import java.io.Serializable;

public class NationRateDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int nationRateKey;		//국제전화요금_키

    private String nationNameEn;		//국가명_영문

    private String nationNameKr;		//국가명_국문

    private String rateSecond;			//요금_초당

    private String rateMinute;			//요금_분당

    private String nationCode;			//국가코드

    private String sdate;				//기준일자

    private String schVal;				//국가명입력값

    private String selVal;				//셀렉트옵션값

    public int getNationRateKey() {
        return nationRateKey;
    }

    public void setNationRateKey(int nationRateKey) {
        this.nationRateKey = nationRateKey;
    }

    public String getNationNameEn() {
        return nationNameEn;
    }

    public void setNationNameEn(String nationNameEn) {
        this.nationNameEn = nationNameEn;
    }

    public String getNationNameKr() {
        return nationNameKr;
    }

    public void setNationNameKr(String nationNameKr) {
        this.nationNameKr = nationNameKr;
    }

    public String getRateSecond() {
        return rateSecond;
    }

    public void setRateSecond(String rateSecond) {
        this.rateSecond = rateSecond;
    }

    public String getRateMinute() {
        return rateMinute;
    }

    public void setRateMinute(String rateMinute) {
        this.rateMinute = rateMinute;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getSchVal() {
        return schVal;
    }

    public void setSchVal(String schVal) {
        this.schVal = schVal;
    }

    public String getSelVal() {
        return selVal;
    }

    public void setSelVal(String selVal) {
        this.selVal = selVal;
    }


}
