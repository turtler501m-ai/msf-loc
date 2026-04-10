package com.ktmmobile.mcp.ktDc.dto;

public class KtDcDto {

    private String rateCd;	//요금제 코드
    private String rateNm; //부가서비스명
    private String soc; //soc코드
    private String baseAmt; //할인가격

    public String getRateCd() {
        return rateCd;
    }

    public void setRateCd(String rateCd) {
        this.rateCd = rateCd;
    }

    public String getRateNm() {
        return rateNm;
    }

    public void setRateNm(String rateNm) {
        this.rateNm = rateNm;
    }

    public String getSoc() {
        return soc;
    }

    public void setSoc(String soc) {
        this.soc = soc;
    }

    public String getBaseAmt() {
        return baseAmt;
    }

    public void setBaseAmt(String baseAmt) {
        this.baseAmt = baseAmt;
    }

}

