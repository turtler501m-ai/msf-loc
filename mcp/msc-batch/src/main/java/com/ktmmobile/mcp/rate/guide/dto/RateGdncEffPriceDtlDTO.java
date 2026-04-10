package com.ktmmobile.mcp.rate.guide.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;
import java.util.Date;


@XmlRootElement(name="item")
@XmlType(propOrder = {"rateAdsvcGdncSeq", "jehuPriceReflectCd", "useYn", "cretDt","cretIp","cretId","customMonths"})
public class RateGdncEffPriceDtlDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long rateAdsvcGdncSeq;
    /** 제휴혜택 코드 */
    private String jehuPriceReflectCd;
    /** 노출여부 */
    private String useYn;
    /** 생성IP */
    private String cretIp;
    /** 생성일시 */
    private Date cretDt;
    /** 생성자ID */
    private String cretId;
    /** 프로모션 개월 수*/
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

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getCretIp() {
        return cretIp;
    }

    public void setCretIp(String cretIp) {
        this.cretIp = cretIp;
    }

    public Date getCretDt() {
        return cretDt;
    }

    public void setCretDt(Date cretDt) {
        this.cretDt = cretDt;
    }

    public String getCretId() {
        return cretId;
    }

    public void setCretId(String cretId) {
        this.cretId = cretId;
    }

    public String getCustomMonths() {
        return customMonths;
    }

    public void setCustomMonths(String customMonths) {
        this.customMonths = customMonths;
    }
}
