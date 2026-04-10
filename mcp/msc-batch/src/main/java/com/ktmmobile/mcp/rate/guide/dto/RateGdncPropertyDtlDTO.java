package com.ktmmobile.mcp.rate.guide.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;
import java.util.Date;


@XmlRootElement(name="item")
@XmlType(propOrder = {"rateAdsvcGdncSeq", "sortOdrg", "propertyCode","propertyCodeNm", "propertySbst","cretDt","cretIp","cretId"})
public class RateGdncPropertyDtlDTO implements Serializable {

    private static final long serialVersionUID = 5058950447833322210L;

    private long rateAdsvcGdncSeq;
    /** 정렬순서 */
    private long sortOdrg;
    /** 속성코드 */
    private String propertyCode;

    /** 속성명 */
    private String propertyCodeNm;

    /** 속성내용 */
    private String propertySbst;
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

    public String getPropertyCode() {
        return propertyCode;
    }

    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }

    public String getPropertyCodeNm() {
        return propertyCodeNm;
    }

    public void setPropertyCodeNm(String propertyCodeNm) {
        this.propertyCodeNm = propertyCodeNm;
    }

    public String getPropertySbst() {
        return propertySbst;
    }

    public void setPropertySbst(String propertySbst) {
        this.propertySbst = propertySbst;
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
