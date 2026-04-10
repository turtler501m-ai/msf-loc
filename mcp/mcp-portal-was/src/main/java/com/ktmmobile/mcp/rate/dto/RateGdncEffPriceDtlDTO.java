package com.ktmmobile.mcp.rate.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;
import java.util.Date;


@XmlRootElement(name="item")
@XmlType(propOrder = {"rateAdsvcGdncSeq", "jehuPriceReflectCd","jehuPriceReflectNm","jehuBenefitAmt","jehuImgUrl","promotionAmt","promotionBenefitAmt",
                        "promotionImgUrl","monthlyEffPrice","useYn","customMonths"})
public class RateGdncEffPriceDtlDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long rateAdsvcGdncSeq;
    /** 제휴혜택 코드 */
    private String jehuPriceReflectCd;
    /* 제휴혜택 명 */
    private String jehuPriceReflectNm;
    /* 제휴혜택 가격*/
    private String jehuBenefitAmt;
    /* 제휴혜택 로고*/
    private String jehuImgUrl;
    /*프로모션 혜택 제공 금액*/
    private String promotionAmt;
    /*프로모션 혜택 금액*/
    private String promotionBenefitAmt;
    /*프로모션 혜택 로고*/
    private String promotionImgUrl;
    /*월 체감가*/
    private String monthlyEffPrice;
    /** 노출여부 */
    private String useYn;
    /*프로모션 개월 수*/
    private String customMonths;

    public long getRateAdsvcGdncSeq() {
        return rateAdsvcGdncSeq;
    }

    public void setRateAdsvcGdncSeq(long rateAdsvcGdncSeq) {
        this.rateAdsvcGdncSeq = rateAdsvcGdncSeq;
    }

    public String getJehuPriceReflectCd() {
        return jehuPriceReflectCd;
    }

    public void setJehuPriceReflectCd(String jehuPriceReflectCd) {
        this.jehuPriceReflectCd = jehuPriceReflectCd;
    }

    public String getJehuPriceReflectNm() {
        return jehuPriceReflectNm;
    }

    public void setJehuPriceReflectNm(String jehuPriceReflectNm) {
        this.jehuPriceReflectNm = jehuPriceReflectNm;
    }

    public String getJehuBenefitAmt() {
        return jehuBenefitAmt;
    }

    public void setJehuBenefitAmt(String jehuBenefitAmt) {
        this.jehuBenefitAmt = jehuBenefitAmt;
    }

    public String getJehuImgUrl() {
        return jehuImgUrl;
    }

    public void setJehuImgUrl(String jehuImgUrl) {
        this.jehuImgUrl = jehuImgUrl;
    }

    public String getPromotionAmt() {
        return promotionAmt;
    }

    public void setPromotionAmt(String promotionAmt) {
        this.promotionAmt = promotionAmt;
    }

    public String getPromotionBenefitAmt() {
        return promotionBenefitAmt;
    }

    public void setPromotionBenefitAmt(String promotionBenefitAmt) {
        this.promotionBenefitAmt = promotionBenefitAmt;
    }

    public String getPromotionImgUrl() {
        return promotionImgUrl;
    }

    public void setPromotionImgUrl(String promotionImgUrl) {
        this.promotionImgUrl = promotionImgUrl;
    }

    public String getMonthlyEffPrice() {
        return monthlyEffPrice;
    }

    public void setMonthlyEffPrice(String monthlyEffPrice) {
        this.monthlyEffPrice = monthlyEffPrice;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getCustomMonths() {
        return customMonths;
    }

    public void setCustomMonths(String customMonths) {
        this.customMonths = customMonths;
    }

}
