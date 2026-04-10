package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.io.Serializable;

public class RateDtlInfo implements Serializable {

    private static final long serialVersionUID = -6412942200249049815L;

    /** 요금제 부가서비스 코드 */
    private String rateAdsvcCd;

    /** 요금제명 혹은 부가서비스명 */
    private String rateAdsvcNm;

    // [ASIS] RateAdsvcGdncBasXML, RateAdsvcBnfitGdncDtlXML, RateGdncBannerDtlDTO,
    //        RateAdsvcGdncDtlXML, RateGdncPropertyDtlDTO, RateGiftPrmtListDTO,
    //        RateGdncEffPriceDtlDTO — 미이관 요금제 안내 상세 DTO 의존 클래스 미생성으로 주석 처리

    public String getRateAdsvcCd() {
        return rateAdsvcCd;
    }

    public void setRateAdsvcCd(String rateAdsvcCd) {
        this.rateAdsvcCd = rateAdsvcCd;
    }

    public String getRateAdsvcNm() {
        return rateAdsvcNm;
    }

    public void setRateAdsvcNm(String rateAdsvcNm) {
        this.rateAdsvcNm = rateAdsvcNm;
    }
}
