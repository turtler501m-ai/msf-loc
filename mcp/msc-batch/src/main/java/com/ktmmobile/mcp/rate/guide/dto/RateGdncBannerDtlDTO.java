package com.ktmmobile.mcp.rate.guide.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;
import java.util.Date;


@XmlRootElement(name="item")
@XmlType(propOrder = {"rateAdsvcGdncSeq", "sortOdrg", "bannerSbst", "bannerColor","bannerBackColor","cretDt","cretIp","cretId"})
public class RateGdncBannerDtlDTO implements Serializable {

    private static final long serialVersionUID = 9170057839600544613L;

    /** 요금제부가서비스안내일련번호 */
    private long rateAdsvcGdncSeq;
    /** 정렬순서 */
    private long sortOdrg;
    /** 배너테스트내용 */
    private String bannerSbst;
    /** 텍스트컬러코드 */
    private String bannerColor;
    /** 배경컬러코드 */
    private String bannerBackColor;
    /** 생성IP */
    private String cretIp;

    /** 생성일시 */
    private Date cretDt;
    /** 생성자ID */
    private String cretId;


    public long getRateAdsvcGdncSeq() {
        return rateAdsvcGdncSeq;
    }

    public void setRateAdsvcGdncSeq(long rateAdsvcGdncSeq) {
        this.rateAdsvcGdncSeq = rateAdsvcGdncSeq;
    }

    public long getSortOdrg() {
        return sortOdrg;
    }

    public void setSortOdrg(long sortOdrg) {
        this.sortOdrg = sortOdrg;
    }

    public String getBannerSbst() {
        return bannerSbst;
    }

    public void setBannerSbst(String bannerSbst) {
        this.bannerSbst = bannerSbst;
    }

    public String getBannerColor() {
        return bannerColor;
    }

    public void setBannerColor(String bannerColor) {
        this.bannerColor = bannerColor;
    }

    public String getBannerBackColor() {
        return bannerBackColor;
    }

    public void setBannerBackColor(String bannerBackColor) {
        this.bannerBackColor = bannerBackColor;
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
}
