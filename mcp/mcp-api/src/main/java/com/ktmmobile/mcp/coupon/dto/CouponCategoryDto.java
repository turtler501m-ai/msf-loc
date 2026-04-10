package com.ktmmobile.mcp.coupon.dto;

import java.io.Serializable;

public class CouponCategoryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String rateCd;
    private String rateNm;

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


}
