package com.ktmmobile.mcp.rate.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;
import java.util.Date;

@XmlRootElement(name="item")
@XmlType(propOrder = {"sortOdrg", "bannerSbst", "bannerColor","bannerBackColor"})
public class RateGdncBannerDtlDTO implements Serializable {

    private static final long serialVersionUID = 9170057839600544613L;


    /** 정렬순서 */
    private long sortOdrg;
    /** 배너테스트내용 */
    private String bannerSbst;
    /** 텍스트컬러코드 */
    private String bannerColor;
    /** 배경컬러코드 */
    private String bannerBackColor;





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


}